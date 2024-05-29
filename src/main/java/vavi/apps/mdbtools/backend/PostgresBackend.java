/*
 * Copyright (c) 2005 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.apps.mdbtools.backend;

import vavi.apps.mdbtools.Backend;


/**
 * PostgresBackend.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 050325 nsano initial version <br>
 */
public class PostgresBackend extends Backend {

    /** Postgres data types */
    private static final String[] typeStrings = {
        "Postgres_Unknown 0x00",
        "Bool",
        "Int2",
        "Int4",
        "Int8",
        "Money",
        "Float4",
        "Float8",
        "Timestamp",
        "Postgres_Unknown 0x09",
        "Char",
        "Postgres_Unknown 0x0b",
        "Postgres_Unknown 0x0c",
        "Postgres_Unknown 0x0d",
        "Postgres_Unknown 0x0e",
        "Serial",
        "Postgres_Unknown 0x10"
    };

    /** */
    protected String[] getTypeStrings() {
        return typeStrings;
    }

    /** */
    protected String getRelationshipString(String[] relationships) {
        return "relationships are not supported for postgres";
    }
}
