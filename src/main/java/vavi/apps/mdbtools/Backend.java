/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns
 * Copyright (c) 2004 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.apps.mdbtools;

import java.util.HashMap;
import java.util.Map;

import vavi.apps.mdbtools.Column.Type;
import vavi.apps.mdbtools.backend.AccessBackend;
import vavi.apps.mdbtools.backend.OracleBackend;
import vavi.apps.mdbtools.backend.PostgresBackend;
import vavi.apps.mdbtools.backend.SybaseBackend;
import vavi.util.StringUtil;


/**
 * functions to deal with different backend database engines
 * 
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano initial version <br>
 */
public abstract class Backend {
    
    /** */
    private static Map<String, Backend> backends = new HashMap<String, Backend>();

    /** */
    protected static String name;

    /** */
    protected abstract String[] getTypeStrings();

    /** */
    String getColumnTypeString(Type columnType) {
        if (columnType.getValue() > 0x10) {
            return StringUtil.toHex4(columnType.getValue());
        } else {
            return getTypeStrings()[columnType.getValue()];
        }
    }

    /** */
    protected abstract String getRelationshipString(String[] relationships);

    /** */
    public static Backend getInstance(String name) {
        return backends.get(name);
    }

    /**
     * initializes the mdb_backends hash and loads the builtin
     * backends
     */
    static {
        backends.put(AccessBackend.name, new AccessBackend());
        backends.put(SybaseBackend.name, new SybaseBackend());
        backends.put(OracleBackend.name, new OracleBackend());
        backends.put(PostgresBackend.name, new PostgresBackend());
    }
}

/* */
