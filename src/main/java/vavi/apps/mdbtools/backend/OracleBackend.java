/*
 * Copyright (c) 2005 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.apps.mdbtools.backend;

import java.text.MessageFormat;

import vavi.apps.mdbtools.Backend;


/**
 * OracleBackend.
 * 
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 050325 nsano initial version <br>
 */
public class OracleBackend extends Backend {

    static {
        name = "oracle";
    }

    /* Oracle data types */
    private static final String[] typeStrings = {
        "Oracle_Unknown 0x00",
        "NUMBER",
        "NUMBER",
        "NUMBER",
        "NUMBER",
        "NUMBER",
        "FLOAT",
        "FLOAT",
        "DATE",
        "Oracle_Unknown 0x09",
        "VARCHAR2",
        "BLOB",
        "CLOB",
        "Oracle_Unknown 0x0d",
        "Oracle_Unknown 0x0e",
        "NUMBER",
        "NUMBER"
    };

    /** */
    protected String[] getTypeStrings() {
        return typeStrings;
    }

    /** */
    protected String getRelationshipString(String[] relationships) {
        return MessageFormat.format(
            "ALTER TABLE {0} ADD CONSTRAINT {1}_{2} FOREIGN KEY ({3}) REFERENCES {4}({5})",
            relationships[1],
            relationships[3],
            relationships[1],
            relationships[0],
            relationships[3],
            relationships[2]);
    }
}

/* */
