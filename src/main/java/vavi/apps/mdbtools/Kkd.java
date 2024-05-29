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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.getLogger;


/**
 * Note: This code is mostly garbage right now...just a test to parse out the
 * KKD structures.
 *
 * @author Brian Bruns
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
class Kkd {

    private static final Logger logger = getLogger(Kkd.class.getName());

    long kkd_pg;
    int kkd_rowid;
    List<Column> props;
    List<Column> columns;

    /** */
    List<Column> getColumnProperties(MdbFile mdb, int start) {
        Column prop = new Column();

        this.props = new ArrayList<>();
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
        Map<?, ?> hash = new HashMap<>();

logger.log(Level.DEBUG, "\n data");
logger.log(Level.DEBUG, "-------");
        int len = mdb.readShort(start);
logger.log(Level.DEBUG, "length = " + len);
        int pos = start + 6;
        int end = start + len;
        while (pos < end) {
logger.log(Level.DEBUG, "pos = " + pos);
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
logger.log(Level.DEBUG, "length = " + tmp + " " + col_type + " " + col_num + " " + val_len);
            for (int i = 0;i < val_len;i++) {
                int c = mdb.readByte(pos + i);
                if (!Character.isISOControl((char) c)) {
                    logger.log(Level.DEBUG, "  " + (char) c);
                } else {
                    logger.log(Level.DEBUG, String.format(" %02x", c));
                }
            }
            pos = start + tmp;
            Column prop = this.props.get(col_num);
logger.log(Level.DEBUG, " Property " + prop.name);
        }
        return hash;
    }

    /** */
    void dump(MdbFile mdb) throws IOException {
        int datapos = 0;
        int rowId = this.kkd_rowid;

        mdb.readPage(this.kkd_pg);
        int rows = mdb.readShort(8);
logger.log(Level.DEBUG, "number of rows = " + rows);
        int kkd_start = mdb.readShort(10 + rowId * 2);
logger.log(Level.DEBUG, String.format("kkd start = %04x", kkd_start));
        int kkd_end = mdb.getPageSize();
        for (int i = 0; i < rows; i++) {
            int tmp = mdb.readShort(10 + i * 2);
            if (tmp < mdb.getPageSize() &&
                tmp > kkd_start &&
                tmp < kkd_end) {
                kkd_end = tmp;
            }
        }
logger.log(Level.DEBUG, String.format("kkd end = %04x", kkd_end));
        int pos = kkd_start + 4; // 4 = K K D \0
        while (pos < kkd_end) {
            int tmp = mdb.readShort(pos);
            int row_type = mdb.readShort(pos + 4);
logger.log(Level.DEBUG, "row size = " + tmp + " type = " + row_type);
            if (row_type == 0x80) {
logger.log(Level.DEBUG, "\nColumn Properties");
logger.log(Level.DEBUG, "-----------------");
                getColumnProperties(mdb, pos);
                int i = 0;
                for (Column prop : this.props) {
logger.log(Level.DEBUG, i++ + " " + prop.name);
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
