/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns.
 */

package vavi.apps.mdbtools;


import java.lang.System.Logger;
import java.lang.System.Logger.Level;

import static java.lang.System.getLogger;


/**
 * Sarg.
 *
 * @author Brian Bruns
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
class Sarg {

    private static final Logger logger = getLogger(Sarg.class.getName());

    static final int OP_EQUAL = 1;
    static final int OP_GT = 2;
    static final int OP_LT = 3;
    static final int OP_GTEQ = 4;
    static final int OP_LTEQ = 5;
    static final int OP_LIKE = 6;
    static final int OP_ISNULL = 7;
    static final int OP_NOTNULL = 8;

    int op;
    Object value;

    /** */
    boolean isString(String s) {

        if (op == OP_LIKE) {
            return likeCompare(s, (String) value);
        }
        int rc = ((String) value).compareTo(s);
        switch (op) {
        case OP_EQUAL:
            if (rc == 0) {
                return true;
            }
            break;
        case OP_GT:
            if (rc < 0) {
                return true;
            }
            break;
        case OP_LT:
            if (rc > 0) {
                return true;
            }
            break;
        case OP_GTEQ:
            if (rc <= 0) {
                return true;
            }
            break;
        case OP_LTEQ:
            if (rc >= 0) {
                return true;
            }
            break;
        default:
logger.log(Level.DEBUG, "Calling testSarg on unknown operator. Add code to Sarg:test_string() for operator " + op);
            break;
        }
        return false;
    }

    /** */
    boolean isInt(int i) {
        int v = (int) value;
        switch (op) {
        case OP_EQUAL:
            if (v == i) {
                return true;
            }
            break;
        case OP_GT:
            if (v < i) {
                return true;
            }
            break;
        case OP_LT:
            if (v > i) {
                return true;
            }
            break;
        case OP_GTEQ:
            if (v <= i) {
                return true;
            }
            break;
        case OP_LTEQ:
            if (v >= i) {
                return true;
            }
            break;
        default:
logger.log(Level.DEBUG, "Calling testSarg on unknown operator. Add code to Sarg#test_int() for operator " + op);
            break;
        }
        return false;
    }

    /** */
    boolean isSarg(MdbFile mdb, Column col, int offset, int length) {

        switch (col.type) {
        case BYTE:
            return isInt(mdb.readByte(offset));
        case INT:
            return isInt(mdb.readShort(offset));
        case LONGINT:
            return isInt(mdb.readInt(offset));
        case TEXT:
            String tmpbuf = new String(mdb.getPageBuffer(), offset, 255);
//            int lastchar = length > 255 ? 255 : length;
            return isString(tmpbuf);
        default:
logger.log(Level.DEBUG, "Calling testSarg on unknown type.  Add code to Sarg#testSarg() for type " + col.type);
            break;
        }
        return true;
    }

    /** */
    private static boolean likeCompare(String s, String r) {
        switch (r.charAt(0)) {
        case '\0':
            return s.charAt(0) == '\0';
        case '_':
            // skip one character
            return likeCompare(s.substring(1), r.substring(1));
        case '%':
            // skip any number of characters
            // the strlen(s) + 1 is important so the next call can
            // if there are trailing characters
            for (int i = 0; i < s.length() + 1; i++) {
                if (likeCompare(s.substring(1), r.substring(1))) {
                    return true;
                }
            }
            return false;
        default:
            int i = 0;
            for (; i < r.length(); i++) {
                if (r.charAt(i) == '_' || r.charAt(i) == '%')
                    break;
            }
            if (s.substring(0, i).equals(r)) {
                return false;
            } else {
                return likeCompare(s.substring(i), r.substring(i));
            }
        }
    }
}
