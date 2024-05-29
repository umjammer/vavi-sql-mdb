/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns
 */

package vavi.apps.mdbtools;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import vavi.apps.mdbtools.Column.Type;
import vavi.apps.mdbtools.backend.AccessBackend;


/**
 * functions to deal with different backend database engines
 *
 * @author Brian Bruns
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano initial version <br>
 */
public abstract class Backend {

    /** */
    private static final Map<String, Backend> backends = new HashMap<>();

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
        for (Backend backend : ServiceLoader.load(Backend.class)) {
            backends.put(AccessBackend.class.getName(), backend);
        }
    }
}
