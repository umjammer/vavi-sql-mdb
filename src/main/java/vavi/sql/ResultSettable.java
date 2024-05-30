/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql;

import java.sql.Types;
import java.util.List;


/**
 * Represents a table name and name, type, value of columns for a sql to proceed.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-07-14 nsano initial version <br>
 */
public interface ResultSettable {

    /** Sets a table name */
    default void setTable(String table) {
        throw new UnsupportedOperationException();
    }

    /** Gets column values: 0 origin */
    List<Object[]> getValues();

    /** @param index 0 origin */
    default String columnNameAt(int index) {
        return null;
    }

    /** @param index 0 origin */
    default int columnTypeAt(int index) {
        return Types.OTHER;
    }
}
