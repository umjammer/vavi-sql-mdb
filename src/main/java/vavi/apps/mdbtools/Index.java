/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns.
 */

package vavi.apps.mdbtools;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;

import static java.lang.System.getLogger;


/**
 * Index.
 *
 * @author Brian Bruns
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
class Index {

    private static final Logger logger = getLogger(Index.class.getName());

    enum Order {
        ASC,
        DESC
    }

    static final int IDX_UNIQUE = 0x01;
    static final int IDX_IGNORENULLS = 0x02;
    static final int IDX_REQUIRED = 0x08;

    /** */
    static final int MAX_IDX_COLS = 10;

    int indexNumber;
    String name;
    int indexType;
    int firstPage;
    /** number rows in index */
    int numberOfRows;
    int numberOfKeys;
    int[] keyColNum = new int[MAX_IDX_COLS];
    Order[] keyColOrder = new Order[MAX_IDX_COLS];
    int flags;
    Table table;

    /** */
    private static final int[] idxToText = {
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //   0-  7 0x00-0x07
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //   8- 15 0x09-0x0f
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //  16- 23 0x10-0x17
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //  24- 31 0x19-0x1f
        ' ' , 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, //  32- 39 0x20-0x27
        0x00, 0x00, 0x00, 0x00, 0x00, ' ' , ' ' , 0x00,    //  40- 47 0x29-0x2f
        'V' , 'W' , 'X' , 'Y' , 'Z' , '[' , '\\', ']' ,    //  48- 55 0x30-0x37
        '^' , '_' , 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //  56- 63 0x39-0x3f
        0x00, '`' , 'a' , 'b' , 'd' , 'f' , 'g' , 'h' ,    //  64- 71 0x40-0x47
        'i' , 'j' , 'k' , 'l' , 'm' , 'o' , 'p' , 'r' ,    //  72- 79 0x49-0x4f H
        's' , 't' , 'u' , 'v' , 'w' , 'x' , 'z' , '{' ,    //  80- 87 0x50-0x57 P
        '|' , '}' , '~' , '5' , '6' , '7' , '8' , '9' ,    //  88- 95 0x59-0x5f
        0x00, '`' , 'a' , 'b' , 'd' , 'f' , 'g' , 'h' ,    //  96-103 0x60-0x67
        'i' , 'j' , 'k' , 'l' , 'm' , 'o' , 'p' , 'r' ,    // 014-111 0x69-0x6f h
        's' , 't' , 'u' , 'v' , 'w' , 'x' , 'z' , '{' ,    // 112-119 0x70-0x77 p
        '|' , '}' , '~' , 0x00, 0x00, 0x00, 0x00, 0x00,    // 120-127 0x78-0x7f
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    // 128-135 0x80-0x87
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //         0x88-0x8f
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //         0x90-0x97
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //         0x98-0x9f
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //         0xa0-0xa7
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //         0xa8-0xaf
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //         0xb0-0xb7
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //         0xb8-0xbf
        0x00, 0x00, 0x00, 0x00, 0x00, '`' , 0x00, 0x00,    //         0xc0-0xc7
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //         0xc8-0xcf
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //         0xd0-0xd7
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //         0xd8-0xdf
        0x00, '`' , 0x00, '`' , '`' , '`' , 0x00, 0x00,    //         0xe0-0xe7
        'f' , 'f' , 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    //         0xe8-0xef
        0x00, 0x00, 0x00, 'r' , 0x00, 0x00, 'r' , 0x00,    //         0xf0-0xf7
        0x81, 0x00, 0x00, 0x00, 'x' , 0x00, 0x00, 0x00,    //         0xf8-0xff
    };

    /*
     * JET Red (v4) Index definition byte layouts
     * <pre>
     * Based on:
     *
     * http://jabakobob.net/mdb/table-page.html
     * https://github.com/jahlborn/jackcess
     *
     * plus inspection of JET (Red) 4 databases.  (JET 3 format has fewer
     * fields -- some of the ones below omitted, and others narrower.)
     *
     * See also JET Blue (Extensible Storage Engine) format information:
     *
     * https://github.com/libyal/libesedb/blob/master/documentation/Extensible%20Storage%20Engine%20%28ESE%29%20Database%20File%20%28EDB%29%20format.asciidoc
     *
     * which is a later Microsoft embedded database format with the same
     * early base format.
     *
     * ----------------------------------------------------------------------
     * Index Column Definitions:
     * - for each "non foreign key" index (ie pidx->index_type!=2), a list
     *   of columns indexed
     *
     * Repeated table->num_real_idxs times:
     *
     * Offset   Bytes   Meaning
     * 0x0000   4   UNKNOWN; seems to be type marker, usually 1923 or 0
     *
     * 0x0004       2       Column 1 ID
     * 0x0006       1       Column 1 Flags
     * 0x0007       2       Column 2 ID
     * 0x0009       1       Column 2 Flags
     * 0x000A       2       Column 3 ID
     * 0x000C       1       Column 3 Flags
     * 0x000D       2       Column 4 ID
     * 0x000F       1       Column 4 Flags
     * 0x0010       2       Column 5 ID
     * 0x0012       1       Column 5 Flags
     * 0x0013       2       Column 6 ID
     * 0x0015       1       Column 6 Flags
     * 0x0016       2       Column 7 ID
     * 0x0018       1       Column 7 Flags
     * 0x0019       2       Column 8 ID
     * 0x001B       1       Column 8 Flags
     * 0x001C       2       Column 9 ID
     * 0x001E       1       Column 9 Flags
     * 0x001F       2       Column 10 ID
     * 0x0021       1       Column 10 Flags
     *
     * 0x0022   1   Usage Map row
     * 0x0023   3   Usage Map page (24-bit)
     * 0x0026   4   First index page
     * 0x002A   4   UNKNOWN
     * 0x002E   2   Index Flags
     * 0x0030   4   UNKNOWN; seems to always be 0
     * 0x0034
     *
     * Column ID of 0xFFFF (-1) means "not used" or "end of used columns".
     * Column Flags:
     * - 0x01 = Ascending
     *
     * Index Flags:
     * - 0x0001 = Unique index
     * - 0x0002 = Ignore NULLs
     * - 0x0008 = Required Index
     *
     * ----------------------------------------------------------------------
     * Index Definitions
     * - for each index (normal, primary key, foreign key), details on the
     *   index.
     *
     * - this appears to be the union of information required for normal/
     *   primary key indexes, and the information required for foreign key
     *   indexes.
     *
     * Repeated table->num_idxs times:
     *
     * Offset   Bytes   Meaning
     * 0x0000   4   UNKNOWN; apparently a type marker, usually 1625 or 0
     * 0x0004   4   Logical Index Number
     * 0x0008   4   Index Column Definition Entry
     * 0x000C   1   FK Index Type
     * 0x000D   4   FK Index Number
     * 0x0011   4   FK Index Table Page Number
     * 0x0015   1   Flags: Update Action
     * 0x0016   1   Flags: Delete Action
     * 0x0017   1   Index Type
     * 0x0018   4   UNKNNOWN; seems to always be 0
     * 0x001B
     *
     * Where Index Type is:
     * 0x01 = normal
     * 0x01 = primary key
     * 0x02 = foreign key index reference
     * </pre>
     */

    /** */
    public boolean testSargs(MdbFile mdb, int offset, int len) {
        int c_offset = 0, c_len;

        for (int i = 0; i < numberOfKeys; i++) {
            c_offset++; // the per column null indicator/flags
            Column col = table.columns.get(keyColNum[i] - 1);

            // This will go away eventually

            if (col.type == Column.Type.TEXT) {
                c_len = 0;
                while (mdb.readByte(offset + c_offset + c_len) != 0) {
                    c_len++;
                }
            } else {
                c_len = col.size;
//logger.log(Level.TRACE, "Only text types currently supported. How did we get here?");
            }

            // If we have no cached index values for this column,
            // create them.
            if (!col.sargs.isEmpty() && col.indexSargCache != null) {
                col.indexSargCache = new ArrayList<>();
                for (int j = 0; j < col.sargs.size(); j++) {
                    Sarg sarg = col.sargs.get(j);
                    Sarg idx_sarg = sarg;
//logger.log(Level.TRACE, "calling cache_sarg");
                    cacheSarg(col, sarg, idx_sarg);
                    col.indexSargCache.add(sarg);
                }
            }

            for (int j = 0; j < col.sargs.size(); j++) {
                Sarg sarg = col.indexSargCache.get(j);
                if (sarg.testSarg(mdb, col, offset + c_offset, c_len) == 0) {
                    // sarg didn't match, no sense going on
                    return false;
                }
            }
        }
        return true;
    }

    /** */
    private static String hashText(String text) {
        byte[] b = new byte[text.length()];
        for (int k = 0; k < text.length(); k++) {
            b[k] = (byte) idxToText[text.charAt(k)];
            if (b[k] == 0) {
logger.log(Level.DEBUG, String.format("No translation available for %02x %c", (int) text.charAt(k), text.charAt(k)));
            }
        }
        return new String(b);
    }

    /** */
    private static int swapInt32(int l) {
        int l2 = 0;

        l2 |= (l & 0x000000ff) << 24;
        l2 |= (l & 0x0000ff00) << 8;
        l2 |= (l & 0x00ff0000) >> 8;
        l2 |= (l & 0xff000000) >> 24;

        return l2;
    }

    /** */
    void cacheSarg(Column col, Sarg sarg, Sarg idxSarg) {

        switch (col.type) {
        case TEXT:
            idxSarg.value = hashText((String) sarg.value);
            break;
        case LONGINT:
            idxSarg.value = swapInt32((Integer) sarg.value) | 0x8000;
//logger.log(Level.TRACE, "int " + StringUtil.toHex8(((Integer) sarg.value).intValue()));
            break;
        case INT:
            break;
        default:
            break;
        }
    }

    /** */
    void walk(Table table) throws IOException {
        MdbFile mdb = table.catalogEntry.mdb;

        if (numberOfKeys != 1) {
            return;
        }

        mdb.readPage(firstPage);
        int curPos = 0xf8;

        for (int i = 0; i < numberOfKeys; i++) {
            int marker = mdb.readByte(curPos++);
            Column col = table.columns.get(keyColNum[i] - 1);
logger.log(Level.DEBUG, "marker " + marker + " column " + i + " coltype " + col.type + " col_size " + col.getFixedSize() + " (" + col.size + ")");
        }
    }

    /** */
    public void dump(Table table) throws IOException {

logger.log(Level.DEBUG, "index number     " + indexNumber);
logger.log(Level.DEBUG, "index name       " + name);
logger.log(Level.DEBUG, "index first page " + firstPage);
logger.log(Level.DEBUG, "index rows       " + numberOfRows);
if (indexType == 1) {
 logger.log(Level.DEBUG, "index is a primary key");
}
        for (int i = 0; i < numberOfKeys; i++) {
            Column col = table.columns.get(keyColNum[i] - 1);
logger.log(Level.DEBUG, "Column " + col.name + "(" + keyColNum[i] + ") Sorted " + (keyColOrder[i] == Order.ASC ? "ascending" : "descending") + " Unique: " + ((flags & IDX_UNIQUE) != 0 ? "Yes" : "No"));
        }
        walk(table);
    }
}
