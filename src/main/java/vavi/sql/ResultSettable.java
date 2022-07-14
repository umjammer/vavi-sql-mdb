package vavi.sql;

import java.sql.Types;
import java.util.List;


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
