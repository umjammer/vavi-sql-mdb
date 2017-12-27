/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns.
 * Copyright (c) 2004 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.apps.mdbtools;

import java.io.IOException;
import java.util.ArrayList;

import vavi.util.Debug;
import vavi.util.StringUtil;


/**
 * Index.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
class Index {

    enum Order {
        ASC,
        DESC;
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
    int[] key_col_num = new int[MAX_IDX_COLS];
    Order[] key_col_order = new Order[MAX_IDX_COLS];
    int flags;
    Table table;

    /** */
    private static final int[] idx_to_text = {
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

    /** */
    public boolean testSargs(MdbFile mdb, int offset, int len) {
        int c_offset = 0, c_len;

        for (int i = 0; i < numberOfKeys; i++) {
            c_offset++; // the per column null indicator/flags
            Column col = table.columns.get(key_col_num[i] - 1);

            // This will go away eventually

            if (col.type == Column.Type.TEXT) {
                c_len = 0;
                while (mdb.readByte(offset + c_offset + c_len) != 0) {
                    c_len++;
                }
            } else {
                c_len = col.size;
//Debug.println("Only text types currently supported. How did we get here?");
            }

            // If we have no cached index values for this column,
            // create them.
            if (col.sargs.size() != 0 && col.indexSargCache != null) {
                col.indexSargCache = new ArrayList<>();
                for (int j = 0; j < col.sargs.size(); j++) {
                    Sarg sarg = col.sargs.get(j);
                    Sarg idx_sarg = sarg;
//Debug.println("calling cache_sarg");
                    index_cache_sarg(col, sarg, idx_sarg);
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
    private void index_hash_text(String text, String hash) {
        byte[] b = new byte[text.length()];
        for (int k = 0; k < text.length(); k++) {
            b[k] = (byte) idx_to_text[text.charAt(k)];
            if (b[k] == 0) {
Debug.println("No translation available for " + StringUtil.toHex2(text.charAt(k)) + " " + text.charAt(k));
            }
        }
        hash = new String(b);
    }

    /** */
    private int index_swap_int32(int l) {
        int l2 = 0;

        l2 |= (l & 0x000000ff) << 24;
        l2 |= (l & 0x0000ff00) << 8;
        l2 |= (l & 0x00ff0000) >> 8;
        l2 |= (l & 0xff000000) >> 24;

        return l2;
    }

    /** */
    void index_cache_sarg(Column col, Sarg sarg, Sarg idx_sarg) {

        switch (col.type) {
        case TEXT:
            index_hash_text((String) sarg.value, (String) idx_sarg.value);
            break;
        case LONGINT:
            idx_sarg.value = new Integer(index_swap_int32(((Integer) sarg.value).intValue()) | 0x8000);
//Debug.println("int " + StringUtil.toHex8(((Integer) sarg.value).intValue()));
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
        int cur_pos = 0xf8;

        for (int i = 0; i < numberOfKeys; i++) {
            int marker = mdb.readByte(cur_pos++);
            Column col = table.columns.get(key_col_num[i] - 1);
Debug.println("marker " + marker + " column " + i + " coltype " + col.type + " col_size " + col.getFixedSize() + " (" + col.size + ")");
        }
    }

    /** */
    public void dump(Table table) throws IOException {

Debug.println("index number     " + indexNumber);
Debug.println("index name       " + name);
Debug.println("index first page " + firstPage);
Debug.println("index rows       " + numberOfRows);
if (indexType == 1) {
 Debug.println("index is a primary key");
}
        for (int i = 0; i < numberOfKeys; i++) {
            Column col = table.columns.get(key_col_num[i] - 1);
Debug.println("Column " + col.name + "(" + key_col_num[i] + ") Sorted " + (key_col_order[i] == Order.ASC ? "ascending" : "descending") + " Unique: " + ((flags & IDX_UNIQUE) != 0 ? "Yes" : "No"));
        }
        walk(table);
    }
}

/* */
