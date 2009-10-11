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
 * Field.
 * 
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
class Field {
    Object value;
    int size;
    int start;
    boolean isNull;
    boolean isFixed;
    int column;
    int offset;
}

/* */
