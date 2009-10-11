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
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import vavi.util.Debug;
import vavi.util.StringUtil;


/**
 * Handle.
 * 
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
class MdbFile implements Cloneable {

    static final int PAGE_SIZE = 4096;

    class Statistics {
        boolean collect;
        long pg_reads;
    }

    /** */
    int currentPage;
    /** */
    int rowNumber;
    /** */
    private byte[] pageBuffer = new byte[PAGE_SIZE];
    /** */
    private byte[] altPageBuffer = new byte[PAGE_SIZE];
    /** catalog of tables */
    List<Catalog> catalogs = new ArrayList<Catalog>();
    /** */
    Statistics stats;

    /** */
    static boolean bigEndian = false;
    /** */
    RandomAccessFile raFile;
    /** */
    boolean writable;
    /** */
    String filename;
    /** */
    int jetVersion;
    /** */
    int key;
    /** */
    byte[] password = new byte[14];
    /** */
    Backend backend;
    /** reference count */
    static int referencesCount;

    /** */
    public byte[] getPageBuffer() {
        return pageBuffer;
    }

    /** */
    public byte[] getAltPageBuffer() {
        return altPageBuffer;
    }

    /** */
    private int[] formats;

    private static final int INDEX_PAGE_SIZE = 0;
    private static final int INDEX_ROW_COUNT_OFFSET = 1;
    private static final int INDEX_TAB_NUM_ROWS_OFFSET = 2;
    private static final int INDEX_TAB_NUM_COLS_OFFSET = 3;
    private static final int INDEX_TAB_NUM_IDXS_OFFSET = 4;
    private static final int INDEX_TAB_NUM_RIDXS_OFFSET = 5;
    private static final int INDEX_TAB_USAGE_MAP_OFFSET = 6;
    private static final int INDEX_TAB_FIRST_DPG_OFFSET = 7;
    private static final int INDEX_TAB_COLS_START_OFFSET = 8;
    private static final int INDEX_TAB_RIDX_ENTRY_SIZE = 9;
    private static final int INDEX_COL_FIXED_OFFSET = 10;
    private static final int INDEX_COL_SIZE_OFFSET = 11;
    private static final int INDEX_COL_NUM_OFFSET = 12;
    private static final int INDEX_TAB_COL_ENTRY_SIZE = 13;

    /** */
    int getPageSize() {
        return formats[INDEX_PAGE_SIZE];
    }

    /** */
    int getNumberOfRowsOfTableOffset() {
        return formats[INDEX_TAB_NUM_ROWS_OFFSET];
    }

    /** */
    int getNumberOfColumnsOfTableOffset() {
        return formats[INDEX_TAB_NUM_COLS_OFFSET];
    }

    /** */
    int getNumberOfIndicesOfTableOffset() {
        return formats[INDEX_TAB_NUM_IDXS_OFFSET];
    }

    /** */
    int getNumberOfRealIndicesOfTableOffset() {
        return formats[INDEX_TAB_NUM_RIDXS_OFFSET];
    }

    /** */
    int getUsageMapOfTableOffset() {
        return formats[INDEX_TAB_USAGE_MAP_OFFSET];
    }

    /** */
    int getRowCountOffset() {
        return formats[INDEX_ROW_COUNT_OFFSET];
    }

    /** */
    int getFirstDataPageOfTableOffset() {
        return formats[INDEX_TAB_FIRST_DPG_OFFSET];
    }

    /** */
    int getStartColumnIndexOfTableOffset() {
        return formats[INDEX_TAB_COLS_START_OFFSET];
    }

    /** */
    int getRealIndicesEntrySize() {
        return formats[INDEX_TAB_RIDX_ENTRY_SIZE];
    }

    /** */
    int getFixedSizeOfColumnOffset() {
        return formats[INDEX_COL_FIXED_OFFSET];
    }

    /** */
    int getSizeOfColumnOffset() {
        return formats[INDEX_COL_SIZE_OFFSET];
    }

    /** */
    int getNumberOfColumnOffset() {
        return formats[INDEX_COL_NUM_OFFSET];
    }

    /** */
    int getColumnEntrySizeOfTable() {
        return formats[INDEX_TAB_COL_ENTRY_SIZE];
    }

    /** */
    private static final int[] jet4Constants = {
        4096, 0x0c, 12, 45, 47, 51, 55, 56, 63, 12, 15, 23, 5, 25
    };

    /** */
    private static final int[] jet3Constants = {
        2048, 0x08, 12, 25, 27, 31, 35, 36, 43, 8, 13, 16, 1, 18
    };

    /** */
    private static final int VERSION_JET3 = 0;
    /** */
    private static final int VERSION_JET4 = 1;

    /** */
    public boolean isJet4() {
//Debug.println("jetVersion: " + mdbFile.jetVersion);
        return jetVersion == VERSION_JET4;
    }

    /** */
    public boolean isJet3() {
//Debug.println("jetVersion: " + mdbFile.jetVersion);
        return jetVersion == VERSION_JET3;
    }

    /** */
    int getNumberOfColumns(int startRowIndex) {
        if (isJet4()) {
            return readShort(startRowIndex);
        } else {
            return readByte(startRowIndex);
        }
    }

    /** */
    int getStartColumnNumber() {
        if (isJet4()) {
            return 2;
        } else {
            // data starts at 1
            return 1;
        }
    }

    static final String encoding = "UTF-16LE";

    /** */
    String getJetString(int offset, int length) {
        String text = null;
        if (isJet3()) {
            text = new String(pageBuffer, offset, length);
        } else {
            if (readByte(offset) == 0xff && readByte(offset + 1) == 0xfe) { // Byte Order Mark ???
                text = new String(pageBuffer, offset + 2, length - 2);
            } else {
                try {
                    // convert unicode to ascii, rather sloppily
                    text = new String(pageBuffer, offset, length, encoding);
                } catch (UnsupportedEncodingException e) {
                    throw (RuntimeException) new IllegalStateException().initCause(e);
                }
            }
        }
        return text;
    }

    /**
     * read the next page if offset is > pg_size
     * return true if page was read
     */
    int readPage_if(int position, int offset)
        throws IOException {

        if (position + offset >= getPageSize()) {
            readPage(readInt(4));
            position = 8 - (getPageSize() - position);
        }
        return position;
    }
    
    //----

    /** */
    public Object clone() {
        try {
            MdbFile newMdb = new MdbFile(filename);
            newMdb.stats = null;
            newMdb.catalogs = new ArrayList<Catalog>();
            for (Catalog catalogEntry : catalogs) {
                newMdb.catalogs.add(catalogEntry);
            }
            newMdb.backend = backend;
            referencesCount++;
            return newMdb;
        } catch (IOException e) {
            throw (RuntimeException) new IllegalStateException().initCause(e);
        }
    }

    /** */
    public void close() throws IOException {
        referencesCount--;
        if (referencesCount <= 0) {
            raFile.close();
        }
    }

    /**
     * @system.property mdb.path
     */
    private File findFile(String fileName) throws FileNotFoundException {
        // try the provided file name first
        File file = new File(fileName);
        if (file.exists()) {
            return file;
        }

        // Now pull apart "mdb.path" and try those
        String mdbPath = System.getProperty("mdb.path");
        if (mdbPath == null) {
            throw new IllegalStateException("mdb.path undefined");
        }

        final String ps = System.getProperty("path.separator");
        StringTokenizer st = new StringTokenizer(mdbPath, ps);

        while (st.hasMoreTokens()) {
            String dir = st.nextToken();
            StringBuilder sb = new StringBuilder(dir);
            if (dir.charAt(dir.length() - 1) != '/') {
                sb.append('/');
            }
            sb.append(fileName);
            file = new File(sb.toString());
            if (file.exists()) {
                return file;
            }
        }

        throw new FileNotFoundException("no " + fileName + "in mdb.path");
    }

    /** */
    private static final int[] keys = {
        0x86, 0xfb, 0xec, 0x37, 0x5d, 0x44, 0x9c, 0xfa, 0xc6, 0x5e, 0x28, 0xe6, 0x13, 0xb6
    };

    /** */
    public MdbFile(String filename, boolean writable) throws IOException {
        // need something to bootstrap with, reassign after page 0 is read
        this.formats = jet3Constants;
        this.filename = filename;
        File file = findFile(filename);

        if (writable) {
            this.writable = true;
            this.raFile = new RandomAccessFile(file, "rw");
        } else {
            this.raFile = new RandomAccessFile(file, "r");
        }

        referencesCount++;
        if (readPage(0) == 0) {
            throw new IllegalStateException("Page 0 length is 0");
        }
        this.jetVersion = readInt(0x14);
        if (isJet4()) {
            this.formats = jet4Constants;
        } else {
            this.formats = jet3Constants;
        }

        // get the db encryption key and xor it back to clear text
        this.key = readInt(0x3e);
        this.key ^= 0xe15e01b9;
ByteArrayOutputStream baos = new ByteArrayOutputStream(4);
DataOutputStream dos = new DataOutputStream(baos);
dos.writeInt(key);
Debug.dump(baos.toByteArray(), 0, 4);
        // get the db password located at 0x42 bytes into the file
        for (int pos = 0; pos < 14; pos++) {
            int j = readInt(0x42 + pos);
            j ^= keys[pos];
            if (j != 0) {
                this.password[pos] = (byte) j;
            } else {
                this.password[pos] = '\0';
            }
        }

        //
        this.backend = Backend.getInstance("access");
        //
        this.catalogs = Catalog.readCatalogs(this, Catalog.Type.TABLE);
        //
        this.getRelationships();
    }

    /** */
    public MdbFile(String filename) throws IOException {
        this(filename, false);
    }

    /**
     * mdb_read a wrapper for read that bails if anything is wrong
     */
    public int readPage(long page) throws IOException {
        int length = _readPage(pageBuffer, page);
        return length;
    }

    /** */
    public int readAltPage(long page) throws IOException {
        int length = _readPage(altPageBuffer, page);
        return length;
    }

    /** */
    private int _readPage(byte[] buffer, long page) throws IOException {

        long offset = page * getPageSize();
//Debug.println("offset: " + StringUtil.toHex8((int) offset) + ", page: " + page);

        if (this.raFile.length() < offset) {
            throw new IllegalArgumentException("offset " + offset + " is beyond EOF: " + page);
        }
        if (stats != null && stats.collect) {
            stats.pg_reads++;
        }

        raFile.seek(offset);	// SEEK_SET
        int length = raFile.read(buffer, 0, getPageSize());
        if (length == -1) {
            throw new EOFException("unexpected EOF");
        } else if (length < getPageSize()) {
            throw new EOFException("EOF reached " + length + " bytes returned. " + getPageSize());
        }
//Debug.println("page: " + page + ": " + length + " bytes:\n");
//Debug.println("page: " + page + ": " + length + " bytes:\n" + StringUtil.getDump(buffer, 64));
        return length;
    }

    /** */
    public void swapPageBuffer() {
        byte[] tmpbuf = new byte[PAGE_SIZE];

        System.arraycopy(pageBuffer, 0, tmpbuf, 0, PAGE_SIZE);
        System.arraycopy(altPageBuffer, 0, pageBuffer, 0, PAGE_SIZE);
        System.arraycopy(tmpbuf, 0, altPageBuffer, 0, PAGE_SIZE);
    }

    /** */
    public int readByte(int offset) {
        int c = pageBuffer[offset] & 0xff;
        return c;
    }

    /** */
    int read16Bit(byte[] buffer, int offset) {
        int value = ((buffer[offset + 1] & 0xff) << 8) | (buffer[offset] & 0xff);
//Debug.println("offset: " + StringUtil.toHex4(offset) + "(" + offset + "): " + StringUtil.toHex4(value) + "(" + value + ")");
        return value & 0xffff;
    }

    /** */
    public int readShort(int offset) {
        int i = read16Bit(pageBuffer, offset);

        return i;
    }

    /** */
    public int read24BitMsb(int offset) {
        int l = 0;

        l |= (pageBuffer[offset    ] & 0xff) << 16;
        l |= (pageBuffer[offset + 1] & 0xff) << 8;
        l |= (pageBuffer[offset + 2] & 0xff);

//Debug.println("offset: " + StringUtil.toHex4(offset) + "(" + offset + "): " + StringUtil.toHex8(l) + "(" + l + ")");
        return l;
    }

    /** */
    public int read24Bit(int offset) {
        int l = 0;

        l |= (pageBuffer[offset + 2] & 0xff) << 16;
        l |= (pageBuffer[offset + 1] & 0xff) << 8;
        l |=  pageBuffer[offset    ] & 0xff;

//Debug.println("offset: " + StringUtil.toHex4(offset) + "(" + offset + "): " + StringUtil.toHex8(l) + "(" + l + ")");
        return l;
    }

    /** */
    int read32Bit(byte[] buffer, int offset) {
        long l = 0;

        l |= (buffer[offset + 3] & 0xff) << 24;
        l |= (buffer[offset + 2] & 0xff) << 16;
        l |= (buffer[offset + 1] & 0xff) << 8;
        l |= (buffer[offset    ] & 0xff);

//Debug.println("offset: " + StringUtil.toHex4(offset) + "(" + offset + "): " + StringUtil.toHex8(l) + "(" + l + ")");
        return (int) (l & 0xffffffff);
    }

    /** */
    public int readInt(int offset) {
        int l = read32Bit(pageBuffer, offset);
        return l;
    }

    /** TODO */
    public float readFloat(int offset) {
        byte[] b = new byte[4];
        System.arraycopy(pageBuffer, offset, b, 0, 4);

//        if (bigEndian) {
//            byte[] b2 = new byte[4];
//            for (int i = 0; i < 4; i++) {
//                b2[i] = b[4 - 1 - i];
//            }
//        }

        int f = 0;
        for (int i = 0; i < 4; i++) {
            f |= (b[i] & 0xff) << (8 * i);
        }

        return Float.intBitsToFloat(f);
    }

    /** TODO */
    public double readDouble(int offset) {
        byte[] b = new byte[8];
        System.arraycopy(pageBuffer, offset, b, 0, 8);

//        if (bigEndian) {
//            byte[] b2 = new byte[8];
//            for (int i = 0; i < 8; i++) {
//                b2[i] = b[8 - 1 - i];
//            }
//        }

        long d = 0;
        for (int i = 0; i < 8; i++) {
            d |= ((long) (b[i] & 0xff)) << (8 * i);
        }

//Debug.println("double: " + Double.longBitsToDouble(d));
        return Double.longBitsToDouble(d);
    }

    //---- index

    /**
     * find the next leaf page if any given a chain. Assumes any exhausted
     * leaf  pages at the end of the chain have been peeled off before the
     * call.
     */
    IndexPage find_next_leaf(List<IndexPage> pages) throws IOException {

        IndexPage indexPage = pages.get(pages.size() - 1);

        // If we are at the first page deep and it's not an index page then
        // we are simply done. (there is no page to find

        readPage(indexPage.page);
        if (pageBuffer[0] == IndexPage.PAGE_LEAF) {
            return indexPage;
        }

        // apply sargs here, currently we don't
        int passed = 0;
        do {
            indexPage.length = 0;
//System.err.println("finding next on pg " + ipg.pg);
            if (indexPage.findNextOnPage(this) == 0) {
                return null;
            }
            int pg = read24BitMsb(indexPage.offset + indexPage.length - 3);
//System.err.println("Looking at pg " + pg + " at " + ipg.offset + " " + ipg.len);
            indexPage.offset += indexPage.length;

            // add to the chain and call this function recursively.
            IndexPage newipg = new IndexPage();
            pages.add(newipg);
            newipg.page = pg;
            newipg = find_next_leaf(pages);
//System.err.println("returning pg " + newipg.pg);
            return newipg;
        } while (passed == 0);
    }

    /**
     * the main index function.
     * caller provides an index chain which is the current traversal of index
     * pages from the root page to the leaf.  Initially passed as blank,
     * mdb_index_find_next will store it's state information here. Each
     * invocation then picks up where the last one left off, allowing us to
     * scroll through the index one by one.
     *
     * Sargs are applied here but also need to be applied on the whole row b/c
     * text columns may return false positives due to hashing and non-index
     * columns with sarg values can't be tested here.
     */
    int index_find_next(Index index, List<IndexPage> pages, int[] page, int[] row)
        throws IOException {

        boolean passed = false;
        IndexPage indexPage = null;
        // if it's new use the root index page (idx.first_pg)
        if (pages.size() == 0) {
            indexPage = new IndexPage();
            pages.add(indexPage);
            indexPage.page = index.firstPage;
            if ((indexPage = find_next_leaf(pages)) == null) {
                return 0;
            }
        } else {
            indexPage = pages.get(pages.size() - 1);
            indexPage.length = 0;
        }

        readPage(indexPage.page);

        // loop while the sargs don't match
        do {
            indexPage.length = 0;
            // if no more rows on this leaf, try to find a new leaf
            if (indexPage.findNextOnPage(this) == 0) {
//System.err.println("page " + ipg.pg + " finished");
                if (pages.size() == 1) {
                    return 0;
                }
                // unwind the stack until we find something or reach the top.
                while (pages.size() > 1) {
// TODO                    pages.cur_depth--;
                    if ((indexPage = find_next_leaf(pages)) == null) {
                        return 0;
                    }
                    indexPage.findNextOnPage(this);
                }
                if (pages.size() == 1) {
                    return 0;
                }
            }
            row[0] = pageBuffer[indexPage.offset + indexPage.length - 1];
            page[0] = read24BitMsb(indexPage.offset + indexPage.length - 4);

            passed = index.testSargs(this, indexPage.offset, indexPage.length);

            indexPage.offset += indexPage.length;
        } while (passed);

//Debug.println("len = " + ipg.len + " pos " + ipg.mask_pos);
//Debug.dump(mdb.pg_buf, ipg.offset, ipg.offset + ipg.len - 1);

        return indexPage.length;
    }

    //---- stats

    /** */
    void stats_on() {
        if (stats == null) {
            stats = new Statistics();
        }

        stats.collect = true;
    }

    /** */
    void stats_off() {
        if (stats == null) {
            return;
        }

        stats.collect = false;
    }

    /** */
    void dump_stats() {
        if (stats == null) {
            return;
        }
Debug.println("Physical Page Reads: " + stats.pg_reads);
    }

    //---- data

    /**
     * Search the previous "row start" values for the first non-deleted
     * one. If we don't find one, then the end of the page is the correct
     * value.
     */
    public int findEndRowIndex(int row) {
        int endRow;

        if (row == 0) {
            endRow = getPageSize() - 1;
        } else {
            endRow = (readShort(((getRowCountOffset() + 2) + (row - 1) * 2)) & 0x0fff) - 1;
        }
        return endRow;
    }

    //----

    /** loop over each entry in the catalog */
    private Table getTable(String name) throws IOException {
        for (Catalog catalog : this.catalogs) {
            if (catalog.type == Catalog.Type.TABLE && catalog.name.equals(name)) {
                return new Table(catalog);
            }
        }

        throw new IllegalStateException(name + " not found");
    }

    /** */
    private String getRelationships() throws IOException {
        
        String text = null;
        
        // child column
        // child table
        // parent column
        // parent table
        String[] relationships = new String[4];

        // generate relationships by "reading" the MSysRelationships table
        // szColumn contains the column name of the child table
        // szObject contains the table name of the child table
        // szReferencedColumn contains the column name of the parent table
        // szReferencedObject contains the table name of the parent table

        /** */
        Table table = getTable("MSysRelationships");
        for (Object[] values : table.fetchRows()) {
            for (int i = 0; i < values.length; i++) {
                Column column = table.columns.get(i);
                if (column.name.equals("szColumn")) {
                    relationships[0] = String.valueOf(values[i]);
                } else if (column.name.equals("szObject")) {
                    relationships[1] = String.valueOf(values[i]);
                } else if (column.name.equals("szReferencedColumn")) {
                    relationships[2] = String.valueOf(values[i]);
                } else if (column.name.equals("szReferencedObject")) {
                    relationships[3] = String.valueOf(values[i]);
                }
            }
            text = backend.getRelationshipString(relationships);
        }
        return text;
    }

    //----

    /** */
    public static void main(String[] args) throws IOException {
        MdbFile mdb = new MdbFile(args[0]);
Debug.println(StringUtil.paramStringDeep(mdb));

        Table table = mdb.getTable(args[1]);
        for (Object[] values : table.fetchRows()) {
            for (int c = 0; c < table.columns.size(); c++) {
                Column column = table.columns.get(c);
                Object value = values[c];
                System.err.println(c + ": " + column.name + ": " + column.type + ": " + value);
            }
        }
    }
}

/* */
