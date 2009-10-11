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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vavi.util.Debug;
import vavi.util.StringUtil;


/**
 * Note: This code is mostly garbage right now...just a test to parse out the
 * KKD structures.
 * 
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
class Kkd {

    long kkd_pg;
    int kkd_rowid;
    List<Column> props;
    List<Column> columns;

    /** */
    List<?> getColumnProperties(MdbFile mdb, int start) {
        Column prop = new Column();
        
        this.props = new ArrayList<Column>();
        int len = mdb.readShort(start);
        int pos = start + 6;
        while (pos < start + len) {
            int tmp = mdb.readShort(pos); // length of string
            pos += 2;
            int cplen = tmp;
            prop.name = new String(mdb.getPageBuffer(), pos, cplen);
            pos += tmp;
            this.props.add(prop);
        }
        return this.props;
    }

    /** */
    Map<?, ?> getColumnDefinitions(MdbFile mdb, int start) {
        Map<?, ?> hash = new HashMap<Object, Object>();
        
Debug.println("\n data");
Debug.println("-------");
        int len = mdb.readShort(start);
Debug.println("length = " + len);
        int pos = start + 6;
        int end = start + len;
        while (pos < end) {
Debug.println("pos = " + pos);
            start = pos;
            int tmp = mdb.readShort(pos); // length of field
            pos += 2;
            int col_type = mdb.readShort(pos); // ???
            pos += 2;
            int col_num = 0;
            if (col_type != 0) {
                col_num = mdb.readShort(pos);
                pos += 2;
            }
            int val_len = mdb.readShort(pos);
            pos += 2;
Debug.println("length = " + tmp + " " + col_type + " " + col_num + " " + val_len);
            for (int i = 0;i < val_len;i++) {
                int c = mdb.readByte(pos + i);
                if (!Character.isISOControl((char) c)) {
                    Debug.println("  " + (char) c);
                } else {
                    Debug.println(" " + StringUtil.toHex2(c));
                }
            }
            pos = start + tmp;
            Column prop = this.props.get(col_num);
Debug.println(" Property " + prop.name);
        }
        return hash;
    }

    /** */
    void dump(MdbFile mdb) throws IOException {
        int datapos = 0;
        int rowId = this.kkd_rowid;
        
        mdb.readPage(this.kkd_pg);
        int rows = mdb.readShort(8);
Debug.println("number of rows = " + rows);
        int kkd_start = mdb.readShort(10 + rowId * 2);
Debug.println("kkd start = " + kkd_start + " " + StringUtil.toHex4(kkd_start));
        int kkd_end = mdb.getPageSize();
        for (int i = 0; i < rows; i++) {
            int tmp = mdb.readShort(10 + i * 2);
            if (tmp < mdb.getPageSize() &&
                tmp > kkd_start &&
                tmp < kkd_end) {
                kkd_end = tmp;
            }
        }
Debug.println("kkd end = " + kkd_end + " " + StringUtil.toHex4(kkd_end));
        int pos = kkd_start + 4; // 4 = K K D \0
        while (pos < kkd_end) {
            int tmp = mdb.readShort(pos);
            int row_type = mdb.readShort(pos + 4);
Debug.println("row size = " + tmp + " type = " + row_type);
            if (row_type == 0x80) {
Debug.println("\nColumn Properties");
Debug.println("-----------------");
                getColumnProperties(mdb, pos);
                int i = 0;
                for (Column prop : this.props) {
Debug.println(i++ + " " + prop.name);
                }
            }
            if (row_type == 0x01) {
                datapos = pos;
            }
            pos += tmp;
        }
        
        if (datapos != 0) {
            getColumnDefinitions(mdb, datapos);
        }
    }
}

/* */
