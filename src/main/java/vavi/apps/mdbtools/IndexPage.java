/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns.
 */

package vavi.apps.mdbtools;


/**
 * IndexPage.
 *
 * @author Brian Bruns
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
    int maskPos;
    int maskByte;
    int maskBit;
    int offset;
    int length;

    /** */
    public IndexPage() {
        offset = 0xf8; // start byte of the index entries
        maskPos = 0x16;
        maskBit = 0;
        length = 0;
    }

    /**
     * find the next entry on a page (either index or leaf).
     * Uses state information stored in the IndexPage across calls.
     */
    public int findNextOnPage(MdbFile mdb) {
        do {
//logger.log(Level.TRACE, ipg.maskBit + " " + ipg.maskByte);
            maskBit++;
            if (maskBit == 8) {
                maskBit = 0;
                maskPos++;
            }
            maskByte = mdb.readByte(maskPos);
            length++;
        } while (maskPos <= 0xf8 && ((1 << maskBit) & maskByte) == 0);
        
        if (maskPos >= 0xf8) {
            return 0;
        }
        
        return length;
    }
}
