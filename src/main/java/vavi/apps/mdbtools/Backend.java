/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns
 */

package vavi.apps.mdbtools;

import java.util.HashMap;
import java.util.Map;

import vavi.apps.mdbtools.Column.Type;
import vavi.apps.mdbtools.backend.AccessBackend;
import vavi.apps.mdbtools.backend.OracleBackend;
import vavi.apps.mdbtools.backend.PostgresBackend;
import vavi.apps.mdbtools.backend.SybaseBackend;


/**
 * functions to deal with different backend database engines
 *
 * @author Brian Bruns
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano initial version <br>
 */
public abstract class Backend {

    /** */
    private static Map<String, Backend> backends = new HashMap<>();

    /** */
    protected abstract String[] getTypeStrings();

    /** */
    String getColumnTypeString(Type columnType) {
        if (columnType.getValue() > 0x10) {
            return String.format("%04x", columnType.getValue());
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

    /*
     * initializes the mdb_backends hash and loads the builtin
     * backends
     */
    static {
        backends.put(AccessBackend.class.getName(), new AccessBackend());
        backends.put(SybaseBackend.class.getName(), new SybaseBackend());
        backends.put(OracleBackend.class.getName(), new OracleBackend());
        backends.put(PostgresBackend.class.getName(), new PostgresBackend());
    }
}

/* */
