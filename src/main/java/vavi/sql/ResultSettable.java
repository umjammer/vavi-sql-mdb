/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql;

import java.sql.Types;
import java.util.List;


/**
 * ResultSettable.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-07-14 nsano initial version <br>
 */
public interface ResultSettable {

    default void setTable(String table) {
        throw new UnsupportedOperationException();
    }

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
