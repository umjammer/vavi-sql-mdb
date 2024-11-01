/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns.
 */

package vavi.apps.mdbtools;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.getLogger;


/**
 * Column.
 *
 * @author Brian Bruns
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
public class Column {

    private static final Logger logger = getLogger(Column.class.getName());

    static int UNDEFINED = -1;

    enum Type {
        BOOL(0x01, 1, 1),
        BYTE(0x02, 3, UNDEFINED),
        INT(0x03, 5, 2),
        LONGINT(0x04, 7, 4),
        MONEY(0x05, 12, 8),
        FLOAT(0x06, 10, 4),
        DOUBLE(0x07, 10, 8),
        SDATETIME(0x08, 20, 4),
        UNKNOWN_09(0x09, UNDEFINED, UNDEFINED),
        TEXT(0x0a, UNDEFINED, UNDEFINED),
        OLE(0x0b, UNDEFINED, 0),
        MEMO(0x0c, 255, UNDEFINED),
        REPID(0x0f, UNDEFINED, 0),
        NUMERIC(0x10, UNDEFINED, 0);
        final int value;
        final int displaySize;
        final int fixedSize;
        Type(int value, int displaySize, int fixedSize) {
            this.value = value;
            this.displaySize = displaySize;
            this.fixedSize = fixedSize;
        }
        int getValue() {
            return value;
        }
        int getDisplaySize() {
            return displaySize;
        }
        int getFixedSize() {
            return fixedSize;
        }
        static Type valueOf(int value) {
            for (Type type : values()) {
                if (type.value == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException(String.valueOf(value));
        }
    }

    public String getName() {
        return name;
    }

    /** */
    String name;
    /** */
    Type type;
    /** */
    int size;

    /** */
    List<Sarg> sargs = new ArrayList<>();
    /** */
    List<Sarg> indexSargCache = new ArrayList<>();

    /** */
    private boolean fixed;
    boolean longAuto;
    boolean uuidAuto;

    /** */
    int queryOrder;
    /** */
    int number;

    // numeric only

    /** */
    int precision;
    /** */
    int scale;

    int varColNum;
    int rowColNum;

    int fixedOffset;

    /** */
    public boolean testSargs(MdbFile mdb, int offset, int len) {

        for (Sarg sarg : sargs) {
            if (!sarg.isSarg(mdb, this, offset, len)) {
                // sarg didn't match, no sense going on
                return false;
            }
        }

        return true;
    }

    /** */
    void addSarg(Sarg sarg) {
        sargs.add(sarg);
    }

    /** */
    int getDisplaySize() {
        return switch (type) {
            case TEXT -> size;
            default -> {
                logger.log(Level.DEBUG, "unknown type: " + type);
                yield type.getDisplaySize() != UNDEFINED ? type.getDisplaySize() : 0;
            }
        };
    }

    /** */
    int getFixedSize() {
        return type.getFixedSize();
    }

    /** */
    boolean isFixed() {
        return fixed;
    }

    /** */
    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    /** TODO */
    public int getLengthOfValue(Object value) {
        return String.valueOf(value).length();
    }

    /** */
    boolean isSizeOfColumnNeeded(Type columnType) {
        return columnType == Type.TEXT;
    }
}
