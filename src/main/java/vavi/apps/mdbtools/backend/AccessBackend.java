/*
 * Copyright (c) 2005 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.apps.mdbtools.backend;

import vavi.apps.mdbtools.Backend;


/**
 * AccessBackend.
 * 
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 050325 nsano initial version <br>
 */
public class AccessBackend extends Backend {

    static {
        name = "accsess";
    }

    /** Access data types */
    private static final String[] typeStrings = {
        "Unknown 0x00",
        "Boolean",
        "Byte",
        "Integer",
        "Long Integer",
        "Currency",
        "Single",
        "Double",
        "DateTime (Short)",
        "Unknown 0x09",
        "Text",
        "OLE",
        "Memo/Hyperlink",
        "Unknown 0x0d",
        "Unknown 0x0e",
        "Replication ID",
        "Numeric"
    };

    /** */
    protected String[] getTypeStrings() {
        return typeStrings;
    }

    /** */
    protected String getRelationshipString(String[] relationships) {
        return "relationships are not supported for access";
    }
}

/* */
