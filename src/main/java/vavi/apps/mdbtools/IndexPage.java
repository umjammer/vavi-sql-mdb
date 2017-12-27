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
 * IndexPage.
 * 
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040117 nsano ported from mdbtool <br>
 */
class IndexPage {

    static final int PAGE_DB = 0;
    static final int PAGE_DATA = 1;
    static final int PAGE_TABLE = 2;
    static final int PAGE_INDEX = 3;
    static final int PAGE_LEAF = 4;
    static final int PAGE_MAP = 5;

    int page;
    int mask_pos;    
    int mask_byte;
    int mask_bit;
    int offset;
    int length;

    /** */
    public IndexPage() {
        offset = 0xf8; // start byte of the index entries
        mask_pos = 0x16;
        mask_bit = 0;
        length = 0;
    }

    /**
     * find the next entry on a page (either index or leaf).
     * Uses state information stored in the IndexPage across calls.
     */
    public int findNextOnPage(MdbFile mdb) {
        do {
//System.err.println(ipg.mask_bit + " " + ipg.mask_byte);
            mask_bit++;
            if (mask_bit == 8) {
                mask_bit = 0;
                mask_pos++;
            }
            mask_byte = mdb.readByte(mask_pos);
            length++;
        } while (mask_pos <= 0xf8 && ((1 << mask_bit) & mask_byte) == 0);
        
        if (mask_pos >= 0xf8) {
            return 0;
        }
        
        return length;
    }
}

/* */
