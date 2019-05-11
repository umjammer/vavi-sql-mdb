/*
 * Copyright (c) 2005 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.apps.mdbtools.backend;

import vavi.apps.mdbtools.Backend;


/**
 * SybaseBackend.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 050325 nsano initial version <br>
 */
public class SybaseBackend extends Backend {

    /** Sybase/MSSQL data types */
    private static final String[] typeStrings = {
        "Sybase_Unknown 0x00",
        "bit",
        "char",
        "smallint",
        "int",
        "money",
        "real",
        "float",
        "smalldatetime",
        "Sybase_Unknown 0x09",
        "varchar",
        "varbinary",
        "text",
        "Sybase_Unknown 0x0d",
        "Sybase_Unknown 0x0e",
        "Sybase_Replication ID",
        "numeric"
    };

    /** */
    protected String[] getTypeStrings() {
        return typeStrings;
    }

    /** */
    protected String getRelationshipString(String[] relationships) {
        return "relationships are not supported for sybase";
    }
}

/* */
