/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns.
 * Copyright (c) 2004 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.apps.mdbtools;


/**
 * Sarg.
 * 
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
class Sarg {

    static final int OP_EQUAL = 1;
    static final int OP_GT = 2;
    static final int OP_LT = 3;
    static final int OP_GTEQ = 4;
    static final int OP_LTEQ = 5;
    static final int OP_LIKE = 6;
    static final int OP_ISNULL = 7;
    static final int OP_NOTNULL = 8;

    int    op;
    Object value;

    /** */
    int test_string(String s) {
        
        if (op == OP_LIKE) {
            return likeCompare(s, (String) value);
        }
        int rc = ((String) value).compareTo(s);
        switch (op) {
        case OP_EQUAL:
            if (rc == 0) {
                return 1;
            }
            break;
        case OP_GT:
            if (rc < 0) {
                return 1;
            }
            break;
        case OP_LT:
            if (rc > 0) {
                return 1;
            }
            break;
        case OP_GTEQ:
            if (rc <= 0) {
                return 1;
            }
            break;
        case OP_LTEQ:
            if (rc >= 0) {
                return 1;
            }
            break;
        default:
System.err.println("Calling mdb_test_sarg on unknown operator. Add code to mdb_test_string() for operator " + op);
            break;
        }
        return 0;
    }

    /** */
    int test_int(int i) {
        int v = ((Integer) value).intValue();
        switch (op) {
        case OP_EQUAL:
            if (v == i) {
                return 1;
            }
            break;
        case OP_GT:
            if (v < i) {
                return 1;
            }
            break;
        case OP_LT:
            if (v > i) {
                return 1;
            }
            break;
        case OP_GTEQ:
            if (v <= i) {
                return 1;
            }
            break;
        case OP_LTEQ:
            if (v >= i) {
                return 1;
            }
            break;
        default:
System.err.println("Calling mdb_test_sarg on unknown operator. Add code to mdb_test_int() for operator " + op);
            break;
        }
        return 0;
    }

    /** */
    int testSarg(MdbFile mdb, Column col, int offset, int length) {

        switch (col.type) {
        case BYTE:
            return test_int(mdb.readByte(offset));
        case INT:
            return test_int(mdb.readShort(offset));
        case LONGINT:
            return test_int(mdb.readInt(offset));
        case TEXT:
            String tmpbuf = new String(mdb.getPageBuffer(), offset, 255);
//            int lastchar = length > 255 ? 255 : length;
            return test_string(tmpbuf);
        default:
System.err.println("Calling mdb_test_sarg on unknown type.  Add code to mdb_test_sarg() for type " + col.type);
            break;
        }
        return 1;
    }

    /** */
    private int likeCompare(String s, String r) {
        int ret;
        
        switch (r.charAt(0)) {
        case '\0':
            if (s.charAt(0) == '\0') {
                return 1;
            } else {
                return 0;
            }
        case '_':
            // skip one character
            return likeCompare(s.substring(1), r.substring(1));
        case '%':
            // skip any number of characters
            // the strlen(s) + 1 is important so the next call can
            // if there are trailing characters
            for (int i = 0; i < s.length() + 1; i++) {
                if (likeCompare(s.substring(1), r.substring(1)) != 0) {
                    return 1;
                }
            }
            return 0;
        default:
            int i = 0;
            for (; i < r.length(); i++) {
                if (r.charAt(i) == '_' || r.charAt(i) == '%')
                    break;
            }
            if (s.substring(0, i).equals(r)) {
                return 0;
            } else {
                ret = likeCompare(s.substring(i), r.substring(i));
                return ret;
            }
        }
    }
}

/* */
