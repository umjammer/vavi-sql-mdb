/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns.
 */

package vavi.apps.mdbtools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vavi.util.Debug;


/**
 * Catalog Entry
 *
 * @author Brian Bruns
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
class Catalog {

    enum Type {
        FORM(0),
        TABLE(1),
        MACRO(2),
        SYSTEM_TABLE(3),
        REPORT(4),
        QUERY(5),
        LINKED_TABLE(6),
        MODULE(7),
        RELATIONSHIP(8),
        UNKNOWN_09(9),
        UNKNOWN_0A(10),
        DATABASE_PROPERTY(11),
        ANY(-1);
        final int value;
        Type(int value) {
            this.value = value;
        }
        int getValue() {
            return value;
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

    /** */
    MdbFile mdb;

    /** */
    String name;
    /** */
    Type type;
    /** */
    long tablePage;
    /** */
    int flag;

    /** */
    Catalog(MdbFile mdb, String name, Type type, int tablePage, int flag) {
        this.mdb = mdb;
        this.name = name;
        this.type = type;
        this.tablePage = tablePage;
        this.flag = flag;
//Debug.println(StringUtil.paramString(this));
    }

    /** */
    public static List<Catalog> readCatalogs(MdbFile mdb, Type objectType) throws IOException {

        List<Catalog> catalogs = new ArrayList<>();

        // dummy up a catalog entry so we may read the table def
        Catalog catalog = new Catalog(mdb, "MSysObjects", Type.TABLE, 2, 0);

        Table table = new Table(catalog);
//catalog.dumpTable();
//table.columns.forEach(c -> Debug.print(c.name + ":" + c.type + ", "));
//Debug.println();
        for (Object[] values : table.fetchRows()) {
//Debug.println("values: " + StringUtil.paramString(values));
            int type = (Short) values[3];
            if (objectType == Type.ANY || type == objectType.getValue()) {
                catalog = new Catalog(
                    mdb,
                    (String) values[2],
                    Type.valueOf(type & 0x7f),
                    (Integer) values[0] & 0x00ffffff,
                    (Integer) values[7]);

                catalogs.add(catalog);
            }
        }

        return catalogs;
    }

    /** */
    void dumpTable() throws IOException {
        Table table = new Table(this);
//Debug.println("**** name           = " + name);
//Debug.println("definition page     = " + tablePage);
//Debug.println("number of datarows  = " + table.numberOfRows);
//Debug.println("number of columns   = " + table.numberOfColumns);
//Debug.println("number of indices   = " + table.numberOfRealIndices);
//Debug.println("first data page     = " + table.firstDataPage);

        table.readIndices();

        for (int i = 0; i < table.numberOfColumns; i++) {
            Column column = table.columns.get(i);

//Debug.println("column " + i + " Name: " + column.name + " Type: " + mdb.backend.getColumnTypeString(column.type) + "(" + column.size + ")");
        }

        for (int i = 0; i < table.numberOfIndices; i++) {
            Index index = table.indices.get(i);
            index.dump(table);
        }
        if (table.usageMap != null) {
Debug.println("pages reserved by this object");
            int pgnum = mdb.read32Bit(table.usageMap, 1);
            // the first 5 bytes of the usage map mean something
            int columnNumber = 0;
            for (int i = 5; i < table.usageMap.length; i++) {
                for (int bitNumber = 0; bitNumber < 8; bitNumber++) {
                    if ((table.usageMap[i] & (1 << bitNumber)) != 0) {
                        columnNumber++;
                        System.err.print(pgnum + " ");
                        if (columnNumber == 10) {
                            System.err.println();
                            columnNumber = 0;
                        }
                    }
                    pgnum++;
                }
            }
        }
    }

    /** */
    public boolean isUserTable() {
        return type == Type.TABLE && (flag & 0x80000002) == 0;
    }

    /** */
    public boolean isSystemTable() {
        return type == Type.TABLE && (flag & 0x80000002) != 0;
    }

    /** */
    public String toString() {
        return getClass().getName() + ": " + type + ", " + name;
    }
}

/* */
