/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns.
 */

package vavi.apps.mdbtools;

import java.io.IOException;

import vavi.util.Debug;


/**
 * Writer.
 *
 * @author Brian Bruns
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
class Writer {

    /** */
    void _mdb_put_int16(byte[] buf, int offset, int value) {
        buf[offset] = (byte) (value % 256);
        value /= 256;
        buf[offset + 1] = (byte) (value % 256);
    }

    /** */
    void _mdb_put_int32(byte[] buf, int offset, int value) {
        buf[offset] = (byte) (value % 256);
        value /= 256;
        buf[offset + 1] = (byte) (value % 256);
        value /= 256;
        buf[offset + 2] = (byte) (value % 256);
        value /= 256;
        buf[offset + 3] = (byte) (value % 256);
    }

    /** */
    int writePage(MdbFile mdb, long page) throws IOException {
        int offset = (int) (page * mdb.getPageSize());

        // is page beyond current size + 1 ?
        if (mdb.raFile.length() < offset + mdb.getPageSize()) {
            throw new IllegalArgumentException("offset " + offset + " is beyond EOF");
        }
        mdb.raFile.seek(offset); // SEEK_SET
        mdb.raFile.write(mdb.getPageBuffer(), 0, mdb.getPageSize());
        return mdb.getPageSize();
    }

    /** */
    int crackRow(Table table, int startRowIndex, int endRowIndex, Field[] fields) {
        Catalog entry = table.catalogEntry;
        MdbFile mdb = entry.mdb;
        int numberOfColumns = mdb.getNumberOfColumns(startRowIndex);

        int totcols = 0;
        int variableColumns = 0; // mdb.pageBuffer[row_end-1];
        int fixedColumns = 0; // numberOfColumns - variableColumns;
        for (int i = 0; i < table.numberOfColumns; i++) {
            Column col = table.columns.get(i);
            if (col.isFixed()) {
                fixedColumns++;
                fields[totcols].column = i;
                fields[totcols].size = col.size;
                fields[totcols++].isFixed = true;
            }
        }
        for (int i = 0; i < table.numberOfColumns; i++) {
            Column column = table.columns.get(i);
            if (!column.isFixed()) {
                variableColumns++;
                fields[totcols].column = i;
                fields[totcols++].isFixed = false;
            }
        }

        int bitmaskSize = (numberOfColumns - 1) / 8 + 1;
        int nullMask = endRowIndex - bitmaskSize + 1;

        for (int i = 0; i < numberOfColumns; i++) {
            int byteNumber = i / 8;
            int bitNumber = i % 8;
            // logic on nulls is reverse, 1 is not null, 0 is null
            fields[i].isNull = (mdb.readByte(nullMask + byteNumber) & (1 << bitNumber)) != 0 ? false : true;
//Debug.println("col " + i + " is " + (fields[i].is_null ? "null" : "not null"));
        }

        int eod, len; // end of data

        // find the end of data pointer
        if (mdb.isJet4()) {
            eod = mdb.readShort(endRowIndex - 3 - variableColumns * 2 - bitmaskSize);
        } else {
            eod = mdb.readByte(endRowIndex - 1 - variableColumns - bitmaskSize);
        }
//Debug.println("eod is " +  eod);

        int col_start = mdb.getStartColumnNumber();
        // actual cols on this row
        int fixed_cols_found = 0;
        int var_cols_found = 0;

        int next_col;
        int var_entry_pos;
        totcols = 0;
        // loop through fixed columns and add values to fields[]
        for (int j = 0; j < table.numberOfColumns; j++) {
            Column col = table.columns.get(j);
            if (col.isFixed() && ++fixed_cols_found <= fixedColumns) {
                fields[totcols].start = startRowIndex + col_start;
                fields[totcols++].value = null; /* mdb.pg_buf[row_start + col_start]; */ // TODO
                if (col.type != Column.Type.BOOL) {
                    col_start += col.size;
                }
            }
        }
        for (int j = 0; j < table.numberOfColumns; j++) {
            Column col = table.columns.get(j);
            if (!col.isFixed() && ++var_cols_found <= variableColumns) {
                if (var_cols_found == variableColumns) {
                    len = eod - col_start;
//Debug.println("len = " + len + " eod " + eod + " col_start " + col_start);
                } else {
                    if (mdb.isJet4()) {
                        // position of the var table
                        // entry for this column
                        var_entry_pos =
                            endRowIndex -
                            bitmaskSize -
                            var_cols_found * 2 - 2 - 1;
                        next_col = mdb.readShort(var_entry_pos);
                        len = next_col - col_start;
                    } else {
                        var_entry_pos =
                            endRowIndex -
                            bitmaskSize -
                            var_cols_found - 1;
                        len = mdb.readByte(var_entry_pos) - mdb.readByte(var_entry_pos + 1);
//Debug.println("%d %d %d %d\n", mdb.pg_buf[var_entry_pos-1],mdb.pg_buf[var_entry_pos],len, col_start);
                    }
                }
                if (len < 0) {
                    len += 256;
                }
                fields[totcols].start = startRowIndex + col_start;
                fields[totcols].value = null; /* mdb.pg_buf[row_start + col_start]; */ // TODO
                fields[totcols++].size = len;
                col_start += len;
            }
        }

        return numberOfColumns;
    }

    /**
     * fields must be ordered with fixed columns first, then vars, subsorted by
     * column number
     */
    int packRow(Table table, byte[] row_buffer, int num_fields, Field[] fields) {
        int pos = 0;
        int var_cols = 0;

        row_buffer[pos++] = (byte) num_fields;
        for (int i = 0; i < num_fields; i++) {
            if (fields[i].isFixed) {
                fields[i].offset = pos;
                System.arraycopy(fields[i].value, 0, row_buffer, pos, fields[i].size);
                pos += fields[i].size;
            }
        }
        for (int i = 0; i < num_fields; i++) {
            if (!fields[i].isFixed) {
                var_cols++;
                fields[i].offset = pos;
                System.arraycopy(fields[i].value, 0, row_buffer, pos, fields[i].size);
                pos += fields[i].size;
            }
        }

        // EOD
        row_buffer[pos++] = (byte) pos;

        for (int i = num_fields - 1; i >= num_fields - var_cols; i--) {
            row_buffer[pos++] = (byte) (fields[i].offset % 256);
        }

        row_buffer[pos++] = (byte) var_cols;

        char byte_ = 0;
        char bit = 0;
        for (int i = 0; i < num_fields; i++) {
            // column is null is bit is clear (0)
            if (!fields[i].isNull) {
                byte_ |= 1 << bit;
Debug.println(i + " " + bit + " " + (1 << bit) + " " + byte_);
            }
            bit++;
            if (bit == 8) {
                row_buffer[pos++] = (byte) byte_;
                bit = 0;
                byte_ = 0;
            }
        }

        // if we've written any bits to the current byte, flush it
        if (bit != 0) {
            row_buffer[pos++] = (byte) byte_;
        }

        return pos;
    }

    /** */
    int getFreespaceOfPage(MdbFile mdb) {

        int rows, free_start, free_end;

        rows = mdb.readShort(mdb.getRowCountOffset());
        free_start = mdb.getRowCountOffset() + 2 + (rows * 2);
        free_end = mdb.readShort((mdb.getRowCountOffset() + rows * 2)) - 1;
Debug.println("free space left on page = " + (free_end - free_start));

        return (free_end - free_start + 1);
    }

    /** */
    int updateRow(Table table) throws IOException {

        Catalog entry = table.catalogEntry;
        MdbFile mdb = entry.mdb;
        Field[] fields = new Field[256];
        byte[] row_buffer = new byte[4096];

        if (!mdb.writable) {
Debug.println("File is not open for writing");
            return 0;
        }
        int row_start = mdb.readShort((mdb.getRowCountOffset() + 2) + ((table.currentRow - 1) * 2));
        int row_end = mdb.findEndRowIndex(table.currentRow - 1);
        int old_row_size = row_end - row_start;

        row_start &= 0x0fff; // remove flags

Debug.println("page " + table.currentPhysicalPage + " row " + (table.currentRow - 1) + " start " + row_start + " end " + row_end);
Debug.dump(mdb.getPageBuffer(), row_start, row_end);

        Object[] values = table.fetchRows().get(table.currentRow - 1);
        for (int i = 0; i < table.numberOfColumns; i++) {
            if (values[i] != null && table.isColumnIndexed(i)) {
Debug.println("Attempting to update column that is part of an index");
                return 0;
            }
        }
        int num_fields = crackRow(table, row_start, row_end, fields);

for (int i = 0; i < num_fields; i++) {
 Debug.println("col " + i + " " + fields[i].column + " start " + fields[i].start + " size " + fields[i].size);
}
        for (int i = 0; i < table.numberOfColumns; i++) {
            Column column = table.columns.get(i);
            if (values[i] != null) {
Debug.println("yes");
                fields[i].value = values[i];
                fields[i].size = column.getLengthOfValue(values[i]);
            }
        }

        int new_row_size = packRow(table, row_buffer, num_fields, fields);

Debug.dump(row_buffer, 0, new_row_size - 1);

        int delta = new_row_size - old_row_size;
        if ((getFreespaceOfPage(mdb) - delta) < 0) {
Debug.println("No space left on this page, update will not occur");
            return 0;
        }
        // do it!
        replaceRow(table, table.currentRow - 1, row_buffer, new_row_size);
        return 1;
    }

    /** */
    int replaceRow(Table table, int row, byte[] new_row, int new_row_size) throws IOException {
        Catalog entry = table.catalogEntry;
        MdbFile mdb = entry.mdb;

Debug.dump(mdb.getPageBuffer(), 39);
Debug.dump(mdb.getPageBuffer(), mdb.getPageSize() - 160, mdb.getPageSize() - 1);
Debug.println("updating row " + row + " on page " + table.currentPhysicalPage);

        byte[] new_pg = new byte[mdb.getPageSize()];

        new_pg[0] = 0x01;
        new_pg[1] = 0x01;
        _mdb_put_int32(new_pg, 4, (int) entry.tablePage);

        int num_rows = mdb.readShort(mdb.getRowCountOffset());
        _mdb_put_int16(new_pg, mdb.getRowCountOffset(), num_rows);

        int pos = mdb.getPageSize();

        // rows before
        for (int i = 0;i < row; i++) {
            int row_start = mdb.readShort((mdb.getRowCountOffset() + 2) + (i * 2));
            int row_end = mdb.findEndRowIndex(i);
            int row_size = row_end - row_start + 1;
            pos -= row_size;
            System.arraycopy(mdb.getPageBuffer(), row_start, new_pg, pos, row_size);
            _mdb_put_int16(new_pg, (mdb.getRowCountOffset() + 2) + (i * 2), pos);
        }

        // our row
        pos -= new_row_size;
        System.arraycopy(new_row, 0, new_pg, pos, new_row_size);
        _mdb_put_int16(new_pg, (mdb.getRowCountOffset() + 2) + (row * 2), pos);

        // rows after
        for (int i = row + 1; i < num_rows; i++) {
            int row_start = mdb.readShort((mdb.getRowCountOffset() + 2) + (i * 2));
            int row_end = mdb.findEndRowIndex(i);
            int row_size = row_end - row_start + 1;
            pos -= row_size;
            System.arraycopy(mdb.getPageBuffer(), row_start, new_pg, pos, row_size);
            _mdb_put_int16(new_pg, (mdb.getRowCountOffset() + 2) + (i * 2), pos);
        }

        // almost done, copy page over current
        System.arraycopy(new_pg, 0, mdb.getPageBuffer(), 0, mdb.getPageSize());

        _mdb_put_int16(mdb.getPageBuffer(), 2, getFreespaceOfPage(mdb));

Debug.dump(mdb.getPageBuffer(), 39);
Debug.dump(mdb.getPageBuffer(), mdb.getPageSize() - 160, mdb.getPageSize() - 1);

        // drum roll, please
        if (writePage(mdb, table.currentPhysicalPage) == 0) {
Debug.println("write failed! exiting...");
            System.exit(1);
        }

        return 1;
    }
}

/* */
