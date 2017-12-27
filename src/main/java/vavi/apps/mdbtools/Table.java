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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import vavi.apps.mdbtools.Column.Type;
import vavi.util.Debug;
import vavi.util.StringUtil;


/**
 * Table.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
class Table {

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
    int basePageIndexMap;
    /** */
    int indexMapSize;
    /** */
    byte[] idx_usage_map;

    /** */
    private Comparator<Column> columnComparator = new Comparator<Column>() {
        public int compare(Column c1, Column c2) {
            return c1.number - c2.number;
        }
    };

    /** */
    Table(Catalog catalogEntry) throws IOException {

        this.catalogEntry = catalogEntry;
        this.name = catalogEntry.name;

        MdbFile mdb = catalogEntry.mdb;

        mdb.readPage(catalogEntry.tablePage);
        int length = mdb.readShort(8);
//Debug.println("length: " + length);
        this.numberOfRows = mdb.readInt(mdb.getNumberOfRowsOfTableOffset());
        this.numberOfColumns = mdb.readShort(mdb.getNumberOfColumnsOfTableOffset());
        this.numberOfIndices = mdb.readInt(mdb.getNumberOfIndicesOfTableOffset());
        this.numberOfRealIndices = mdb.readInt(mdb.getNumberOfRealIndicesOfTableOffset());

        // grab a copy of the usage map
        int rowNumber = mdb.readByte(mdb.getUsageMapOfTableOffset());
        mdb.readAltPage(mdb.read24Bit(mdb.getUsageMapOfTableOffset() + 1));
        mdb.swapPageBuffer();
        int startRow = mdb.readShort((mdb.getRowCountOffset() + 2) + (rowNumber * 2));
        int endRow = mdb.findEndRowIndex(rowNumber);
        this.usageMap = new byte[endRow - startRow + 1];
        System.arraycopy(mdb.getPageBuffer(), startRow, usageMap, 0, usageMap.length);
//Debug.println("usageMap: " + (endRow - startRow) + " bytes\n" + StringUtil.getDump(mdb.getPageBuffer(), startRow, endRow - startRow));
        // swap back
        mdb.swapPageBuffer();
//Debug.println("usage map found on page " + mdb.read24Bit(mdb.getUsageMapOfTableOffset() + 1) + " start " + StringUtil.toHex4(startRow) + " end " + StringUtil.toHex4(endRow));

        this.firstDataPage = mdb.readShort(mdb.getFirstDataPageOfTableOffset());

        this.columns = readColumns();
//Debug.println("table: [" + name + "]\n" + StringUtil.paramString(this));
    }

    /** */
    private List<Column> readColumns() throws IOException {

        MdbFile mdb = catalogEntry.mdb;
        List<Column> sortedColumns = new ArrayList<>();

        columns = new ArrayList<>();

        int currentColumnPosition = mdb.getStartColumnIndexOfTableOffset() + (numberOfRealIndices * mdb.getRealIndicesEntrySize());
//Debug.println("currentColumnPosition: " + currentColumnPosition);

        // new code based on patch submitted by Tim Nelson 2000.09.27

        // column attributes
        for (int i = 0; i < numberOfColumns; i++) {
//Debug.println("column " + i);
//dumpData(mdb.pageBuffer, cur_col ,cur_col + 18);
            Column column = new Column();
            column.number = mdb.readByte(currentColumnPosition + mdb.getNumberOfColumnOffset());

            currentColumnPosition = mdb.readPage_if(currentColumnPosition, 0);
            column.type = Column.Type.valueOf(mdb.readByte(currentColumnPosition));

            if (column.type == Column.Type.NUMERIC) {
                column.precision = mdb.readByte(currentColumnPosition + 11);
                column.scale = mdb.readByte(currentColumnPosition + 12);
            }

            currentColumnPosition = mdb.readPage_if(currentColumnPosition, 13);
            column.setFixed((mdb.readByte(currentColumnPosition + mdb.getFixedSizeOfColumnOffset()) & 0x01) != 0);
            if (column.type != Column.Type.BOOL) {
                currentColumnPosition = mdb.readPage_if(currentColumnPosition, 17);
                int lowByte = mdb.readByte(currentColumnPosition + mdb.getSizeOfColumnOffset());
                currentColumnPosition = mdb.readPage_if(currentColumnPosition, 18);
                int highByte = mdb.readByte(currentColumnPosition + mdb.getSizeOfColumnOffset() + 1);
                column.size += (highByte << 8) | lowByte;
            } else {
                column.size = 0;
            }

            sortedColumns.add(column);
            currentColumnPosition += mdb.getColumnEntrySizeOfTable();
//Debug.println("column[" + i + "]: " + StringUtil.paramString(column));
        }

        int currentNamePosition = currentColumnPosition;
//Debug.println("currentNamePosition: " + currentNamePosition);

        Collections.sort(sortedColumns, columnComparator);

        // column names
        for (int i = 0; i < numberOfColumns; i++) {
            // fetch the column
            Column column = sortedColumns.get(i);

            // we have reached the end of page
            currentNamePosition = mdb.readPage_if(currentNamePosition, 0);
            int nameSize = mdb.readByte(currentNamePosition);

            if (mdb.isJet4()) {
                // FIX ME - for now just skip the high order byte
                currentNamePosition += 2;
                // determine amount of name on this page
                int length = ((currentNamePosition + nameSize) > mdb.getPageSize()) ?
                    mdb.getPageSize() - currentNamePosition :
                    nameSize;

                // strip high order (second) byte from unicode string
                column.name = new String(mdb.getPageBuffer(), currentNamePosition, length, MdbFile.encoding);
                // name wrapped over page
                if (length < nameSize) {
                    // read the next page
                    mdb.readPage(mdb.readInt(4));
                    currentNamePosition = 8 - (mdb.getPageSize() - currentNamePosition);
                    // get the rest of the name
                    column.name += new String(mdb.getPageBuffer(), currentNamePosition, nameSize - length, MdbFile.encoding);
                }

                currentNamePosition += nameSize;
            } else if (mdb.isJet3()) {
                // determine amount of name on this page
                int length = ((currentNamePosition + nameSize) > mdb.getPageSize()) ?
                    mdb.getPageSize() - currentNamePosition :
                    nameSize;

                if (length != 0) {
                    column.name = new String(mdb.getPageBuffer(), currentNamePosition + 1, length);
                }
                // name wrapped over page
                if (length < nameSize) {
                    // read the next pg
                    mdb.readPage(mdb.readInt(4));
                    currentNamePosition = 8 - (mdb.getPageSize() - currentNamePosition);
                    // get the rest of the name
                    column.name = new String(mdb.getPageBuffer(), currentNamePosition, nameSize - length);
                }

                currentNamePosition += nameSize + 1;
            } else {
Debug.println("Unknown MDB version");
            }
//Debug.println("column[" + i + "]: " + StringUtil.paramString(column));
        }

        startIndexIndex = currentNamePosition;
//Debug.println("startIndexIndex " + startIndexIndex);
        return sortedColumns;
    }

    //---- sarg

    /** */
    int add_sarg_by_name(String columnName, Sarg sarg) {

        for (int i = 0;i < columns.size();i++) {
            Column column = columns.get(i);
            if (column.name.equals(columnName)) {
                column.addSarg(sarg);
                return 1;
            }
        }
        // else didn't find the column return 0!
        return 0;
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
Debug.println("no page read");
                return rows;
            }
        }

        while (true) {
            int rowCount = mdb.readShort(mdb.getRowCountOffset());
//Debug.println("page: " + currntPageNumber + ", physicalPage: " + currentPhysicalPage + ", row: " + currentRow + " / " + rowCount);

            // if at end of page, find a new page
            if (currentRow >= rowCount) {
                currentRow = 0;

                if (readNextDPage() == 0) {
Debug.println("no more page");
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
Debug.println("deleted row: " + row);
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
//if (col.name.equals("Type")) {
// Debug.println("column Type, startColumn " + startColumn + " startRowIndex " + startRowIndex + " data " + mdb.pg_buf[startRowIndex + startColumn] + " " + mdb.pg_buf[startRowIndex + startColumn + 1]);
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
    public int readNextDPage() throws IOException {

        int mapType = usageMap[0];
        if (mapType == 0) {
            return readNextDPageByMap0();
        } else if (mapType == 1) {
            return readNextDPageByMap1();
        } else {
            throw new IllegalArgumentException("unrecognized map type: " + usageMap[0]);
        }
    }

    /** */
    public List<?> readIndices() {
        MdbFile mdb = catalogEntry.mdb;
        int indexNumber, keyNumber, columnNumber;
        int nameSize;

        // FIX ME -- doesn't handle multipage table headers

        indices = new ArrayList<>();

        int currentPosition = startIndexIndex + 39 * numberOfRealIndices;

        for (int i = 0; i < numberOfIndices; i++) {
            Index index = new Index();
            index.table = this;
            index.indexNumber = mdb.readShort(currentPosition);
            currentPosition += 19;
            index.indexType = mdb.readByte(currentPosition++);
            indices.add(index);
        }

        for (int i = 0; i < numberOfIndices; i++) {
            Index pIndex = indices.get(i);
            nameSize = mdb.readByte(currentPosition++);
            pIndex.name = new String(mdb.getPageBuffer(), currentPosition, nameSize);
//Debug.println("index name " + pIndex.name);
            currentPosition += nameSize;
        }

        currentPosition = startIndexIndex;
        indexNumber = 0;
        for (int i = 0; i < numberOfRealIndices; i++) {
            Index index = null;
            do {
                index = indices.get(indexNumber++);
            } while (index != null && index.indexType == 2);

            // if there are more real indexes than index entries left after
            // removing type 2's decrement real indexes and continue.  Happens
            // on Northwind Orders table.
            if (index == null) {
                numberOfRealIndices--;
                continue;
            }

            index.numberOfRows = mdb.readInt(43 + (i * 8));

            keyNumber = 0;
            for (int j = 0; j < Index.MAX_IDX_COLS; j++) {
                columnNumber = mdb.readShort(currentPosition);
                currentPosition += 2;
                if (columnNumber != 0xffff) {
                    // set column number to a 1 based column number and store
                    index.key_col_num[keyNumber] = columnNumber + 1;
                    if (mdb.readByte(currentPosition) != 0) {
                        index.key_col_order[keyNumber] = Index.Order.ASC;
                    } else {
                        index.key_col_order[keyNumber] = Index.Order.DESC;
                    }
                    keyNumber++;
                }
                currentPosition++;
            }
            index.numberOfKeys = keyNumber;
            currentPosition += 4;
            index.firstPage = mdb.readInt(currentPosition);
            currentPosition += 4;
            index.flags = mdb.readByte(currentPosition++);
        }

        return indices;
    }

    //---- data

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

//if ("Name".equals(col.name)) {
// Debug.println("start " + start + " " + len);
//}
        if (length != 0) {
//Debug.println("len " + len + " size " + col.col_size);
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
Debug.println("Reading LVAL page failed");
                return null;
            }
            // swap the alt and regular page buffers, so we can call get_int16
            mdb.swapPageBuffer();
            int row_stop = (ole_row != 0) ? (mdb.readShort(10 + (ole_row - 1) * 2) & 0x0fff) : (mdb.getPageSize() - 1);
            int row_start = mdb.readShort(10 + ole_row * 2);

//Debug.println("row num " + ole_row + ", row start " + StringUtil.toHex4(row_start) + ", row stop " + StringUtil.toHex4(row_stop));

            int length = (row_stop - row_start < 0) ? 0 : row_stop - row_start;
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
//Debug.println("Reading LVAL page " + lval_pg);
            // swap the alt and regular page buffers, so we can call get_int16
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mdb.swapPageBuffer();
            int cur = 0;
            do {
                if (mdb.readPage(lval_pg) != mdb.getPageSize()) {
                    // Failed to read
                    return null;
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
            } while (lval_pg != 0);
            // make sure to swap page back
            mdb.swapPageBuffer();
            return baos.toByteArray();
        } else {
Debug.println("Unhandled ole field flags: " + StringUtil.toHex4(oleFlags));
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
            String text = mdb.getJetString(start + MEMO_OVERHEAD, size - MEMO_OVERHEAD);
//Debug.println("0x" + StringUtil.toHex4(memoFlags) + ": " + text);
            return text;
        } else if ((memoFlags & 0x4000) != 0) {
            // The 16 bit integer at offset 0 is the length of the memo field.
            // The 24 bit integer at offset 5 is the page it is stored on.
            int memo_row = mdb.readByte(start + 4);

            int lval_pg = mdb.read24Bit(start + 5);
//Debug.println("Reading LVAL page " + lval_pg);

            if (mdb.readAltPage(lval_pg) != mdb.getPageSize()) {
Debug.println("failed to read page: " + lval_pg);
                return null;
            }
            // swap the alt and regular page buffers, so we can call get_int16
            mdb.swapPageBuffer();
            int row_stop = (memo_row != 0) ? mdb.readShort(mdb.getRowCountOffset() + 2 + (memo_row - 1) * 2) & 0x0fff : mdb.getPageSize();
            int row_start = mdb.readShort(mdb.getRowCountOffset() + 2 + memo_row * 2);
//Debug.println("row num " + memo_row + " row start " + row_start + " row stop " + row_stop);
            int len = row_stop - row_start;
//Debug.dump(mdb.getPageBuffer(), row_start, len);
            String text = mdb.getJetString(row_start, len);
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
Debug.println("failed to read page: " + lval_pg);
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
            return mdb.getJetString(start, size);
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
}

/* */
