/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns.
 * Copyright (c) 2004 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.apps.mdbtools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;

import vavi.apps.mdbtools.Column.Type;
import vavi.util.Debug;
import vavi.util.StringUtil;


/**
 * Table.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
public class Table {

    /** */
    Catalog catalogEntry;
    /** */
    String name;
    /** */
    int numberOfColumns;
    /** */
    List<Column> columns;
    /** */
    int numberOfRows;
    /** */
    int startIndexIndex;
    /** */
    int numberOfRealIndices;
    /** */
    int numberOfIndices;
    /** */
    List<Index> indices;
    /** */
    int firstDataPage;
    /** */
    int currentPageNumber;
    /** */
    int currentPhysicalPage;
    /** */
    int currentRow;
    /** don't skip deleted rows */
    boolean doNotSkipDeleted;
    /** object allocation map */
    int basePageMap;
    /** */
    byte[] usageMap;
    /** */
    byte[] freeUsageMap;
    /** */
    int basePageIndexMap;
    /** */
    int indexMapSize;
    /** */
    byte[] indexUsageMap;

    public List<Column> getColumns() {
        return columns;
    }

    /** */
    private Comparator<Column> columnComparator = (c1, c2) -> c1.number - c2.number;

    /** */
    Table(Catalog catalogEntry) throws IOException {

        this.catalogEntry = catalogEntry;
        this.name = catalogEntry.name;

        MdbFile mdb = catalogEntry.mdb;

        mdb.readPage(catalogEntry.tablePage);
        if (mdb.getPageBuffer()[0] != 0x02) {
            throw new IllegalStateException("not a valid table def page[" + catalogEntry.tablePage + "]: " + mdb.getPageBuffer()[0]);
        }
        int length = mdb.readShort(8);
//Debug.println("length: " + length);
        this.numberOfRows = mdb.readInt(mdb.getNumberOfRowsOfTableOffset());
        int num_var_cols = mdb.readShort(mdb.getNumberOfColumnsOfTableOffset() - 2);
        this.numberOfColumns = mdb.readShort(mdb.getNumberOfColumnsOfTableOffset());
        this.numberOfIndices = mdb.readInt(mdb.getNumberOfIndicesOfTableOffset());
        this.numberOfRealIndices = mdb.readInt(mdb.getNumberOfRealIndicesOfTableOffset());

        // grab a copy of the usage map
        int rowNumber = mdb.readInt(mdb.getTableUsageMapOffset());
        int[] startRow = new int[1];
        int[] mapSize = new int[1];
        byte[] buf = mdb.find_pg_row(rowNumber, startRow, mapSize);
//Debug.println("mapSize: " + mapSize[0]);
        this.usageMap = new byte[mapSize[0]];
        System.arraycopy(buf, startRow[0], usageMap, 0, usageMap.length);
//Debug.println("usageMap: " + (endRow - startRow) + " bytes\n" + StringUtil.getDump(mdb.getPageBuffer(), startRow, endRow - startRow));
//Debug.println("usage map found on page " + mdb.read24Bit(mdb.getUsageMapOfTableOffset() + 1) + " start " + StringUtil.toHex4(startRow) + " end " + StringUtil.toHex4(endRow));

        // grab a copy of the free space page map
        rowNumber = mdb.readInt(mdb.getTableFreeMapOffset());
        buf = mdb.find_pg_row(rowNumber, startRow, mapSize);
        this.freeUsageMap = new byte[mapSize[0]];
        System.arraycopy(buf, startRow[0], freeUsageMap, 0, freeUsageMap.length);

        this.firstDataPage = mdb.readShort(mdb.getFirstDataPageOfTableOffset());

        this.columns = readColumns();
//Debug.println("table: [" + name + "]\n" + StringUtil.paramString(this));
    }

    /** */
    private List<Column> readColumns() throws IOException {

        MdbFile mdb = catalogEntry.mdb;
        List<Column> sortedColumns = new ArrayList<>();

        columns = new ArrayList<>();

        byte[] buf = new byte[mdb.getTableColumnEntrySize()];

        int currentPosition = mdb.getTableColumnStartOffset() + (numberOfRealIndices * mdb.getTableRealIndicesEntrySize());
//Debug.println("currentColumnPosition: " + currentColumnPosition);

        // new code based on patch submitted by Tim Nelson 2000.09.27

        // column attributes
        for (int i = 0; i < numberOfColumns; i++) {
//Debug.println("column " + i);
//dumpData(mdb.pageBuffer, cur_col ,cur_col + 18);
            currentPosition = read_pg_if_n(mdb, buf, currentPosition, mdb.getTableColumnEntrySize());
            Column column = new Column();

            column.type = Column.Type.valueOf(buf[0]);

            column.number = buf[mdb.getColumnNumberOffset()];

            column.varColNum = mdb.read16Bit(buf, mdb.getTableColumnOffsetVar());

            column.rowColNum = mdb.read16Bit(buf, mdb.getTableRowColumnNumberOffset());

            if (column.type == Column.Type.NUMERIC) {
                column.precision = buf[11];
                column.scale = buf[12];
            }

            column.setFixed((buf[mdb.getColumnFlagsOffset()] & 0x01) != 0);
            column.longAuto = (buf[mdb.getColumnFlagsOffset()] & 0x04) != 0;
            column.uuidAuto = (buf[mdb.getColumnFlagsOffset()] & 0x40) != 0;

            column.fixedOffset = mdb.read16Bit(buf, mdb.getColumnSizeOffset());

            if (column.type != Column.Type.BOOL) {
                column.size = mdb.read16Bit(buf, mdb.getColumnSizeOffset());
            } else {
                column.size = 0;
            }

            sortedColumns.add(column);
//Debug.println("column[" + i + "]: " + StringUtil.paramString(column));
        }

//Debug.println("currentPosition: " + currentPosition);

        // column names
        for (int i = 0; i < numberOfColumns; i++) {
            // fetch the column
            Column column = sortedColumns.get(i);

            // we have reached the end of page
            int nameSize;
            if (mdb.isJet3()) {
                nameSize = read_pg_if_8(mdb, currentPosition);
                currentPosition++;
            } else {
                nameSize = read_pg_if_16(mdb, currentPosition);
                currentPosition += 2;
            }
            byte[] tmpbuf = new byte[nameSize];
            currentPosition = read_pg_if_n(mdb, tmpbuf, currentPosition, nameSize);
            column.name = mdb.getJetString(tmpbuf, 0, nameSize);
//Debug.println("column[" + i + "]: " + StringUtil.paramString(column));
//Debug.println(mdb.isJet3() + ", column[" + i + "]\n" + StringUtil.getDump(tmpbuf, 0, nameSize));
        }

        sortedColumns.sort(columnComparator);

        startIndexIndex = currentPosition;
//Debug.println("startIndexIndex " + startIndexIndex);
        return sortedColumns;
    }

    //---- sarg

    /**
     * @throws NoSuchElementException didn't find the column
     */
    void addSargByName(String columnName, Sarg sarg) {

        for (Column column : columns) {
            if (column.name.equals(columnName)) {
                column.addSarg(sarg);
            }
        }
        throw new NoSuchElementException(columnName);
    }

    //---- data

    /** */
    public List<Object[]> fetchRows() throws IOException {
        this.currentPageNumber = 0;
        this.currentPhysicalPage = 0;
        this.currentRow = 0;

        MdbFile mdb = catalogEntry.mdb;

        List<Object[]> rows = new ArrayList<>();

        if (numberOfRows == 0) {
            return rows;
        }

        // initialize
        if (currentPageNumber == 0) {
            currentPageNumber = 1;
            currentRow = 0;
            if (readNextDPage() == 0) {
Debug.println(Level.FINE, name + ": no page read");
                return rows;
            }
        }

        while (true) {
            int rowCount = mdb.readShort(mdb.getRowCountOffset());
//Debug.println(name + ": " + "page: " + currentPageNumber + ", physicalPage: " + currentPhysicalPage + ", row: " + currentRow + " / " + rowCount);

            // if at end of page, find a new page
            if (currentRow >= rowCount) {
                currentRow = 0;

                if (readNextDPage() == 0) {
Debug.println(Level.FINER, name + ": no more page");
                    break;
                }
            }

            Object[] values = readRow(currentRow);
            if (values != null) {
                rows.add(values);
            }
            this.currentRow++;
        }

        return rows;
    }

    /** */
    void dumpData() throws IOException {
        for (Object[] values : fetchRows()) {
            for (int j = 0; j < numberOfColumns; j++) {
Debug.println("column " + (j + 1) + " is " + values[j]);
            }
        }
    }

    /**
     * @return null: deleted
     * @throws IllegalArgumentException when column data read error occurs
     */
    private Object[] readRow(int row) throws IOException {

        MdbFile mdb = catalogEntry.mdb;

        int startRowIndex = mdb.readShort((mdb.getRowCountOffset() + 2) + (row * 2));
        int endRowIndex = mdb.findEndRowIndex(row);

        boolean deleteFlag = false;
        boolean lookupFlag = false;
        if ((startRowIndex & 0x8000) != 0) {
            lookupFlag = true;
        }
        if ((startRowIndex & 0x4000) != 0) {
            deleteFlag = true;
        }
        startRowIndex &= 0x0fff; // remove flags

//Debug.println("Row " + row + ", bytes 0x" + StringUtil.toHex4(startRowIndex) + " to 0x" + StringUtil.toHex4(endRowIndex) + " " + (lookupFlag ? "[lookup]" : "") + " " + (deleteFlag ? "[delflag]" : ""));
//Debug.println("here 0: " + doNotSkipDeleted);

        if (!doNotSkipDeleted && deleteFlag) {
Debug.println(Level.FINER, "deleted row: " + row);
            endRowIndex = startRowIndex - 1;
            return null;
        }

//Debug.println("row: 0x" + StringUtil.toHex4(startRowIndex) + " -\n" + StringUtil.getDump(mdb.getPageBuffer(), startRowIndex, endRowIndex - startRowIndex));

        // find out all the important stuff about the row
        this.numberOfColumns = mdb.getNumberOfColumns(startRowIndex);
        Object[] values = new Object[numberOfColumns];
//Debug.println("table: [" + name + "]; numberOfColumns(" + row + "): " + numberOfColumns + ", lookupFlag: " + lookupFlag + ", deleteFlag: " + deleteFlag);
        int variableColumns = 0; // mdb.pageBuffer[endRowIndex - 1];
        int fixedColumns = 0; // numberOfColumns - variableColumns;
        for (int j = 0; j < numberOfColumns; j++) {
            Column col = columns.get(j);
            if (col.isFixed()) {
                fixedColumns++;
            } else {
                variableColumns++;
            }
        }
        int bitmaskSize = (numberOfColumns - 1) / 8 + 1;
        int eod; // end of data
        if (mdb.isJet4()) {
            eod = mdb.readShort(endRowIndex - 3 - variableColumns * 2 - bitmaskSize);
        } else {
            eod = mdb.readByte(endRowIndex - 1 - variableColumns - bitmaskSize);
        }
        byte[] nullMask = new byte[33]; // 256 columns max / 8 bits per byte
        for (int i = 0; i < bitmaskSize; i++) {
            nullMask[i] = mdb.getPageBuffer()[endRowIndex - bitmaskSize + i + 1];
        }

//Debug.println("numberOfColumns: " + numberOfColumns + ", variableColumns " + variableColumns + " EOD " + eod);

        int numberOfJumps = 0, usedJumps = 0;
        int columnPointer, deletedColumns = 0;

        int startColumnIndex = mdb.getStartColumnNumber();
        int foundFixedColumns = 0;
        int foundVariableColumns = 0;

        // fixed columns
        for (int j = 0; j < numberOfColumns; j++) {
            Column column = columns.get(j);
            if (column.isFixed() && (++foundFixedColumns <= fixedColumns)) {
//if (column.name.equals("Type")) {
// Debug.println("column Type, startColumn " + startColumnIndex + " startRowIndex " + startRowIndex + " data " + mdb.getPageBuffer()[startRowIndex + startColumnIndex] + " " + mdb.getPageBuffer()[startRowIndex + startColumnIndex + 1]);
//}
                boolean isNull = isNull(nullMask, j + 1);
                values[j] = getValueOfColumn(mdb, column, isNull, startRowIndex + startColumnIndex, column.size);
                if (column.type != Column.Type.BOOL) {
                    startColumnIndex += column.size;
                }
            }
        }

        // if fixed columns add up to more than 256, we need a jump
        if (startColumnIndex >= 256) {
            numberOfJumps++;
            usedJumps++;
            startRowIndex = startRowIndex + startColumnIndex - (startColumnIndex % 256);
        }

        startColumnIndex = startRowIndex;
        //
        while (startColumnIndex + 256 < endRowIndex - bitmaskSize - 1 - variableColumns - numberOfJumps) {
            startColumnIndex += 256;
            numberOfJumps++;
        }
        if (mdb.isJet4()) {
            columnPointer = endRowIndex - 2 - bitmaskSize - 1;
            eod = mdb.readShort(columnPointer - variableColumns * 2);
            startColumnIndex = mdb.readShort(columnPointer);
        } else {
            columnPointer = endRowIndex - bitmaskSize - numberOfJumps - 1;
            if (mdb.readByte(columnPointer) == 0xff) {
                columnPointer--;
                deletedColumns++;
            }
            eod = mdb.readByte(columnPointer - variableColumns);
            startColumnIndex = mdb.readByte(columnPointer);
        }

//Debug.println("startColumn " + startColumnIndex + " numberOfJumps " + numberOfJumps);

        // variable columns
        for (int j = 0; j < numberOfColumns; j++) {
            Column column = columns.get(j);
            if (!column.isFixed() && (++foundVariableColumns <= variableColumns)) {
                int length;
                if (foundVariableColumns == variableColumns) {
                    length = eod - startColumnIndex;
                } else {
                    if (mdb.isJet4()) {
                        int nextColumn = (mdb.readByte(endRowIndex - bitmaskSize - foundVariableColumns * 2 - 2)     << 8) |
                                          mdb.readByte(endRowIndex - bitmaskSize - foundVariableColumns * 2 - 2 - 1);
                        length = nextColumn - startColumnIndex;

//Debug.println("found " + foundVariableColumns + ", fix " + (endRowIndex - bitmaskSize - foundVariableColumns * 2 - 2 - 1) + ", new pos " + (endRowIndex - bitmaskSize - foundVariableColumns * 2 - 2 - 1 - numberOfJumps * 2) + ", new start " + (endRowIndex - bitmaskSize - foundVariableColumns * 2 - 2 - 1) + ", old start " + startColumnIndex);

                    } else {
                        length = mdb.readByte(columnPointer - foundVariableColumns) - startColumnIndex;
                    }
                }

                boolean isNull = isNull(nullMask, j + 1);

//Debug.println("binding length " + length + ", isNull " + isNull + ", startColumn " + startColumnIndex + ", startRow " + startRowIndex + ", endRow " + endRowIndex + ", bitmask " + bitmaskSize + ", foundVariableColumns " + foundVariableColumns + ", buffer " + (endRowIndex - bitmaskSize - foundVariableColumns * 2 - 1 - numberOfJumps));

                values[j] = getValueOfColumn(mdb, column, isNull, startRowIndex + startColumnIndex, length);
                startColumnIndex += length;
            }
        }

        return values;
    }

    /**
     * @param isNull boolean value when column.type is BOOL
     * @return nullable
     * @throws IllegalStateException
     */
    private Object getValueOfColumn(MdbFile mdb, Column column, boolean isNull, int offset, int length) throws IOException {
        Object result = null;
        if (column.type == Column.Type.BOOL) {
            result = !isNull;
        } else if (column.type == Column.Type.OLE) {
            result = length == 0 ? null : getOleValue(mdb, offset, length);
        } else if (isNull) {
            result = null;
        } else {
            if (!column.testSargs(mdb, offset, length)) {
                throw new IllegalStateException("error? in testSargs: " + column.number);
            } else {
                result = getValue(mdb, offset, column, length);
            }
        }
//Debug.println("column(" + column.number + "): [" + column.name + "], type: " + column.type + ", isNull: " + isNull + ", length: " + length + ", value: [" + result + "]");
        return result;
    }

    /**
     * @return 0 means "not found"
     */
    private int readNextDPageByMap0() throws IOException {

        MdbFile mdb = catalogEntry.mdb;
        int pageNumber = mdb.read32Bit(usageMap, 1);
        // the first 5 bytes of the usage map mean something
        for (int i = 5; i < usageMap.length; i++) {
            for (int bitn = 0; bitn < 8; bitn++) {
                if ((usageMap[i] & (1 << bitn)) != 0 && pageNumber > currentPhysicalPage) {
                    currentPhysicalPage = pageNumber;
                    if (mdb.readPage(pageNumber) == 0) {
                        return 0;
                    } else {
                        return pageNumber;
                    }
                }
                pageNumber++;
            }
        }
        // didn't find anything
        return 0;
    }

    /**
     * @return 0 means "not found"
     */
    private int readNextDPageByMap1() throws IOException {

        MdbFile mdb = catalogEntry.mdb;
        int mapPage;
//      int map_byte;

        int pageNumber = 0;
//Debug.println("map size " + map_sz);
        for (int i = 1; i < usageMap.length - 1; i += 4) {
            mapPage = mdb.read32Bit(usageMap, i);
//Debug.println("loop " + i " pg " + map_pg);

            if (mapPage == 0) {
                continue;
            }

            if (mdb.readAltPage(mapPage) != mdb.getPageSize()) {
                throw new IllegalStateException("didn't get a full page at " + mapPage);
            }
//Debug.println("reading page " + map_pg);
            for (int j = 4; j < mdb.getPageSize(); j++) {
                for (int bitNumber = 0; bitNumber < 8; bitNumber++) {
                    if ((mdb.getAltPageBuffer()[j] & (1 << bitNumber)) != 0 &&
                        pageNumber > currentPhysicalPage) {

                        currentPhysicalPage = pageNumber;
                        if (mdb.readPage(pageNumber) == 0) {
                            return 0;
                        } else {
//Debug.println("page found at pageNumber " + pageNumber);
                            return pageNumber;
                        }
                    }
                    pageNumber++;
                }
            }
        }
        // didn't find anything
//Debug.println("returning 0");
        return 0;
    }

    /**
     * @return 0 means "not found"
     */
    int readNextDPage() throws IOException {

        int mapType = usageMap[0];
        if (mapType == 0) {
            return readNextDPageByMap0();
        } else if (mapType == 1) {
            return readNextDPageByMap1();
        } else {
            throw new IllegalArgumentException("unrecognized map type: " + usageMap[0]);
        }
    }

    /**
     * Read data into a buffer, advancing pages and setting the
     * page cursor as needed. In the case that buf in NULL, pages
     * are still advanced and the page cursor is still updated.
     */
    int read_pg_if_n(MdbFile mdb, byte[] buf, int cur_pos, int len) throws IOException {

        /* Advance to page which contains the first byte */
        while (cur_pos >= mdb.getPageSize()) {
            mdb.readPage(mdb.read32Bit(mdb.getPageBuffer(), 4));
            cur_pos -= (mdb.getPageSize() - 8);
        }
        /* Copy pages into buffer */
        int bp = 0;
        while (cur_pos + len >= mdb.getPageSize()) {
            int piece_len = mdb.getPageSize() - cur_pos;
            if (buf != null) {
                System.arraycopy(mdb.getPageBuffer(), cur_pos, buf, bp, piece_len);
                bp += piece_len;
            }
            len -= piece_len;
            mdb.readPage(mdb.read32Bit(mdb.getPageBuffer(), 4));
            cur_pos = 8;
        }
        /* Copy into buffer from final page */
        if (len != 0 && buf != null) {
            System.arraycopy(mdb.getPageBuffer(), cur_pos, buf, 0, len);
        }
        cur_pos += len;
        return cur_pos;
    }

    int read_pg_if_32(MdbFile mdb, int cur_pos) throws IOException {
        byte[] c = new byte[4];

        read_pg_if_n(mdb, c, cur_pos, 4);
        return mdb.read32Bit(c, 0);
    }

    int read_pg_if_16(MdbFile mdb, int cur_pos) throws IOException {
        byte[] c = new byte[2];

        read_pg_if_n(mdb, c, cur_pos, 2);
        return mdb.read16Bit(c, 0);
    }

    int read_pg_if_8(MdbFile mdb, int cur_pos) throws IOException {
        byte[] c = new byte[1];

        read_pg_if_n(mdb, c, cur_pos, 1);
        return c[0];
    }

    /** */
    List<?> readIndices() throws IOException {
        MdbFile mdb = catalogEntry.mdb;
        int indexNumber, keyNumber, columnNumber;
        int nameSize;
        int indexStartPage = mdb.currentPage;

        // FIX ME -- doesn't handle multipage table headers

        indices = new ArrayList<>();

        int index2size;
        int typeOffset;
        int currentPosition;
        if (mdb.isJet3()) {
            currentPosition = startIndexIndex + 39 * numberOfRealIndices;
            index2size = 20;
            typeOffset = 19;
        } else {
            currentPosition = startIndexIndex + 52 * numberOfRealIndices;
            index2size = 28;
            typeOffset = 23;
        }

        // Read in the definitions of table indexes, into table->indices

        // num_real_idxs should be the number of indexes other than type 2.
        // It's not always the case. Happens on Northwind Orders table.

        byte[] tmpbuf = new byte[index2size];
        for (int i = 0; i < numberOfIndices; i++) {
            currentPosition = read_pg_if_n(mdb, tmpbuf, currentPosition, index2size);
            Index index = new Index();
            index.table = this;
            index.indexNumber = mdb.read16Bit(tmpbuf, 4);
            index.indexType = tmpbuf[typeOffset];
            indices.add(index);
            if (index.indexType != 2) {
                numberOfRealIndices++;
            }
        }

        // Pick up the names of each index
        for (int i = 0; i < numberOfIndices; i++) {
            Index pIndex = indices.get(i);
            if (mdb.isJet3()) {
                nameSize = read_pg_if_8(mdb, currentPosition);
                currentPosition++;
            } else {
                nameSize = read_pg_if_16(mdb, currentPosition);
                currentPosition += 2;
            }
            tmpbuf = new byte[nameSize];
            currentPosition = read_pg_if_n(mdb, tmpbuf, currentPosition, nameSize);
            pIndex.name = mdb.getJetString(tmpbuf, 0, nameSize);
//Debug.println("index name " + pIndex.name);
        }

        // Pick up the column definitions for normal/primary key indexes
        // NOTE: Match should possibly be by index_col_def_num, rather
        // than index_num; but in files encountered both seem to be the
        // same (so left with index_num until a counter example is found).
        mdb.readAltPage(catalogEntry.tablePage);
        mdb.readPage(indexStartPage);
        currentPosition = startIndexIndex;
        indexNumber = 0;
        for (int i = 0; i < numberOfRealIndices; i++) {
            if (!mdb.isJet3()) {
                currentPosition += 4;
            }
            Index index = null;
            int j;
            for (j = 0; j < numberOfIndices; j++) {
                index = indices.get(j);
                if (index.indexType != 2 && index.indexNumber == i) {
                    break;
                }
            }
            if (j == numberOfIndices) {
                continue;
            }

            // if there are more real indexes than index entries left after
            // removing type 2's decrement real indexes and continue. Happens
            // on Northwind Orders table.
            if (index == null) {
                numberOfRealIndices--;
                continue;
            }

            index.numberOfRows = mdb.read32Bit(mdb.getAltPageBuffer(),
                mdb.getTableColumnStartOffset() + (index.indexNumber * mdb.getTableRealIndicesEntrySize()));

            keyNumber = 0;
            for (j = 0; j < Index.MAX_IDX_COLS; j++) {
                columnNumber = mdb.readShort(currentPosition);
                currentPosition += 2;
                if (columnNumber != 0xffff) {
                    currentPosition++;
                    continue;
                }
                // here we have the internal column number that does not
                // always match the table columns because of deletions
                int cleaned_col_num = -1;
                for (int k = 0; k < numberOfColumns; k++) {
                    Column col = columns.get(k);
                    if (col.number == columnNumber) {
                        cleaned_col_num = k;
                        break;
                    }
                }
                if (cleaned_col_num == -1) {
                    Debug.printf("CRITICAL: can't find column with internal id %d in index %s\n", columnNumber, index.name);
                    currentPosition++;
                    continue;
                }

                // set column number to a 1 based column number and store
                index.key_col_num[keyNumber] = cleaned_col_num + 1;
                if (mdb.readByte(currentPosition) != 0) {
                    index.key_col_order[keyNumber] = Index.Order.ASC;
                } else {
                    index.key_col_order[keyNumber] = Index.Order.DESC;
                }
                keyNumber++;
            }
            index.numberOfKeys = keyNumber;
            currentPosition += 4;
            index.firstPage = read_pg_if_32(mdb, currentPosition);
            currentPosition += 4;

            if (!mdb.isJet3()) {
                currentPosition += 4;
            }

            index.flags = read_pg_if_8(mdb, currentPosition++);
            if (!mdb.isJet3()) {
                currentPosition += 5;
            }
        }

        return indices;
    }

    // data

    /**
     * @param nullMask TODO wanna be field
     */
    private boolean isNull(byte[] nullMask, int columnNumber) {
        int byteNumber = (columnNumber - 1) / 8;
        int bitNumber = (columnNumber - 1) % 8;

        if (((1 << bitNumber) & nullMask[byteNumber]) != 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param length
     * @return 0 length String when length is 0, nullable
     */
    private Object getValue(MdbFile mdb, int start, Column column, int length)
        throws IOException {

//if ("Name".equals(column.name)) {
// Debug.println("start " + start + " " + length);
//}
        if (length != 0) {
//Debug.println("len " + length + " size " + column.size);
            if (column.type == Column.Type.NUMERIC) {
                return getNumericValue(mdb, start, column.type, column.precision, column.scale);
            } else {
                return getValue(mdb, start, column.type, length);
            }
        } else {
            return "";
        }
    }

    /** */
    private static final int MEMO_OVERHEAD = 12;

    /**
     * @return null when error
     */
    private byte[] getOleValue(MdbFile mdb, int start, int size)
        throws IOException {

        if (size < MEMO_OVERHEAD) {
            return null;
        }

        int oleLength = mdb.readShort(start);
        int oleFlags = mdb.readShort(start + 2);
//Debug.println("oleFlags: " + StringUtil.toHex4(oleFlags));

        if (oleFlags == 0x8000) {
            int length = size - MEMO_OVERHEAD;
            // inline ole field
            byte[] dest = new byte[length];
            System.arraycopy(mdb.getPageBuffer(), start + MEMO_OVERHEAD, dest, 0, length);
            return dest;
        } else if (oleFlags == 0x4000) {
            // The 16 bit integer at offset 0 is the length of the memo field.
            // The 24 bit integer at offset 5 is the page it is stored on.
            int ole_row = mdb.readByte(start + 4);

            int lval_pg = mdb.read24Bit(start + 5);
//Debug.println("Reading LVAL page " + lval_pg);

            if (mdb.readAltPage(lval_pg) != mdb.getPageSize()) {
                // Failed to read
Debug.println(Level.WARNING, "Reading LVAL page failed");
                return null;
            }
            // swap the alt and regular page buffers, so we can call get_int16
            mdb.swapPageBuffer();
            int row_stop = (ole_row != 0) ? (mdb.readShort(10 + (ole_row - 1) * 2) & 0x0fff) : (mdb.getPageSize() - 1);
            int row_start = mdb.readShort(10 + ole_row * 2);

//Debug.println("row num " + ole_row + ", row start " + StringUtil.toHex4(row_start) + ", row stop " + StringUtil.toHex4(row_stop));

            int length = Math.max(row_stop - row_start, 0);
            byte[] dest = new byte[length];
//Debug.println("length: " + length);
            if (length > 0) {
                System.arraycopy(mdb.getPageBuffer(), row_start, dest, 0, length);
            }
            // make sure to swap page back
            mdb.swapPageBuffer();
            return dest;
        } else if (oleFlags == 0x0000) {
            int ole_row = mdb.readByte(start + 4);
            int lval_pg = mdb.read24Bit(start + 5);
//Debug.printf("0Reading LVAL page %08x", lval_pg);
            // swap the alt and regular page buffers, so we can call get_int16
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mdb.swapPageBuffer();
            int cur = 0;
            do {
try {
                if (mdb.readPage(lval_pg) != mdb.getPageSize()) {
//Debug.println(Level.WARNING, "Failed to read: lval_pg: " + lval_pg);
                    // Failed to read
                    return null;
                }
} catch (IllegalArgumentException e) { // TODO
Debug.printf(Level.WARNING, e.getMessage());
 mdb.swapPageBuffer();
 return baos.toByteArray();
}
                int row_stop = (ole_row != 0) ? (mdb.readShort(10 + (ole_row - 1) * 2) & 0x0fff) : (mdb.getPageSize() - 1);
                int row_start = mdb.readShort(10 + ole_row * 2);
//Debug.println("row num " + ole_row + ", row start " + row_start + ", row stop " + row_stop);

                int length = row_stop - row_start;
                baos.write(mdb.getPageBuffer(), row_start + 4, length - 4);
                cur += length - 4;

                // find next lval page
                ole_row = mdb.readByte(row_start);
                lval_pg = mdb.read24Bit(row_start + 1);
//Debug.printf("nReading LVAL page %08x, %s, %s", lval_pg, (lval_pg & 0x800) == 0, (lval_pg & 0x400) == 0);
            } while (lval_pg != 0 && ((lval_pg & 0x800) == 0 && (lval_pg & 0x400) == 0));
            // make sure to swap page back
            mdb.swapPageBuffer();
            return baos.toByteArray();
        } else {
Debug.printf(Level.WARNING, "Unhandled ole field flags: %04x", oleFlags);
            return null;
        }
    }

    /** */
    private static final int BIND_SIZE = 16384;

    /**
     * @return null when error
     */
    private String getMemoValue(MdbFile mdb, int start, int size)
        throws IOException {

        if (size < MEMO_OVERHEAD) {
            return null;
        }

//Debug.println(StringUtil.getDump(mdb.getPageBuffer(), start, 12));

        int memoLength = mdb.readShort(start);
        int memoFlags = mdb.readShort(start + 2);

        if ((memoFlags & 0x8000) != 0) {
            // inline memo field
//Debug.dump(mdb.getPageBuffer(), start + MEMO_OVERHEAD, size - MEMO_OVERHEAD);
            String text = mdb.getJetString(mdb.getPageBuffer(), start + MEMO_OVERHEAD, size - MEMO_OVERHEAD);
//Debug.println("0x" + StringUtil.toHex4(memoFlags) + ": " + text);
            return text;
        } else if ((memoFlags & 0x4000) != 0) {
            // The 16 bit integer at offset 0 is the length of the memo field.
            // The 24 bit integer at offset 5 is the page it is stored on.
            int memo_row = mdb.readByte(start + 4);

            int lval_pg = mdb.read24Bit(start + 5);
//Debug.println("Reading LVAL page " + lval_pg);

            if (mdb.readAltPage(lval_pg) != mdb.getPageSize()) {
Debug.println(Level.WARNING, "failed to read page: " + lval_pg);
                return null;
            }
            // swap the alt and regular page buffers, so we can call get_int16
            mdb.swapPageBuffer();
            int row_stop = (memo_row != 0) ? mdb.readShort(mdb.getRowCountOffset() + 2 + (memo_row - 1) * 2) & 0x0fff : mdb.getPageSize();
            int row_start = mdb.readShort(mdb.getRowCountOffset() + 2 + memo_row * 2);
//Debug.println("row num " + memo_row + " row start " + row_start + " row stop " + row_stop);
            int len = row_stop - row_start;
//Debug.dump(mdb.getPageBuffer(), row_start, len);
            String text = mdb.getJetString(mdb.getPageBuffer(), row_start, len);
            // make sure to swap page back
            mdb.swapPageBuffer();
            return text;
        } else { /* if (memo_flags == 0x0000) { */
            int memo_row = mdb.readByte(start + 4);
            int lval_pg = mdb.read24Bit(start + 5);
//Debug.println("Reading LVAL page " + lval_pg);
            // swap the alt and regular page buffers, so we can call get_int16
            mdb.swapPageBuffer();
            String text = null;
            do {
                if (mdb.readPage(lval_pg) != mdb.getPageSize()) {
Debug.println(Level.WARNING, "failed to read page: " + lval_pg);
                    return null;
                }
                int row_stop = (memo_row != 0) ? mdb.readShort(10 + (memo_row - 1) * 2) & 0x0fff : mdb.getPageSize() - 1;
                int row_start = mdb.readShort(10 + memo_row * 2);
//Debug.println("row num " + memo_row + " row start " + row_start + " row stop" + row_stop);

                int len = row_stop - row_start;
                text = new String(mdb.getPageBuffer(), row_start + 4,
                                  text.length() + len - 4 > BIND_SIZE ?
                                  BIND_SIZE - text.length() :
                                  len - 4);

                // find next lval page
                memo_row = mdb.readByte(row_start);
                lval_pg = mdb.read24Bit(row_start + 1);
//Debug.println("0x" + StringUtil.toHex4(memoFlags) + ": " + text);
            } while (lval_pg != 0);
            // make sure to swap page back
            mdb.swapPageBuffer();
            return text;
        }
    }

    /**
     * @return numeric String
     */
    private String getNumericValue(MdbFile mdb, int start, Type type, int prec, int scale) {
//Debug.println("numeric: " + String.valueOf(mdb.readInt(start + 13)) + ", start: " + start + ", prec: " + prec + ", scale: " + scale);
        String value = String.valueOf(mdb.readInt(start + 13));
        value = value.substring(0, Math.min(prec, value.length()));

        if (scale == 0) {
            return value;
        } else {
            return value.substring(0, Math.min(prec - scale, value.length())) + "." + value.substring(scale);
        }
    }

    /**
     * @return nullable
     * @throws IllegalArgumentException
     */
    private Object getValue(MdbFile mdb, int start, Column.Type type, int size) throws IOException {
        switch (type) {
        case BYTE:
            return mdb.readByte(start);
        case INT:
        case UNKNOWN_09:
            return (short) mdb.readShort(start);
        case LONGINT:
            return mdb.readInt(start);
        case FLOAT:
            return mdb.readFloat(start);
        case DOUBLE:
            return mdb.readDouble(start);
        case TEXT:
            if (size < 0) {
                throw new IllegalArgumentException(String.valueOf(size));
            }
            return mdb.getJetString(mdb.getPageBuffer(), start, size);
        case SDATETIME:
            double value = mdb.readDouble(start);
            if (value == 0.0) {
                return null;
            } else {
                long t = (long) ((value - 25569.0) * 86400.0) * 1000;
                return new SimpleDateFormat("yyyy-MM-dd").format(new Date(t));
            }
        case MEMO:
            return getMemoValue(mdb, start, size);
        case MONEY:
            return new BigDecimal(mdb.readInt(start));
        case BOOL:
        case NUMERIC:
        default:
            throw new IllegalArgumentException("should not happen: " + type);
        }
    }

    /** */
    public boolean isColumnIndexed(int columnNumber) {

        for (int i = 0; i < numberOfIndices; i++) {
            Index index = indices.get(i);
            for (int j = 0; j < index.numberOfKeys; j++) {
                if (index.key_col_num[j] == columnNumber) {
                    return true;
                }
            }
        }
        return false;
    }

    /* */
    public String toString() {
        return getClass().getName() + ": " + name + "[" + numberOfRows + "], " + StringUtil.paramString(columns);
    }
}

/* */
