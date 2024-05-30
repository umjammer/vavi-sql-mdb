/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;


/**
 * Engine.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-05-30 nsano initial version <br>
 */
public interface Engine {

    /** @return result set */
    ResultSettable result();

    /**
     * @param database table name and columns name, type and value for a sql that this engine will process
     */
    void setDatabase(ResultSettable database);

    /**
     * @param metaData metadata for a sql that this engine will process
     */
    void setDatabaseMetaData(DatabaseMetaData metaData);

    /** Executes the sql. */
    boolean execute(String sql) throws IOException;

    /**
     * Executes the sql.
     * @param params set by a prepared statement
     */
    boolean execute(String sql, Map<Integer, Object> params) throws IOException;

    /** */
    static Engine factory(String name) {
        for (Engine engine : ServiceLoader.load(Engine.class)) {
            if (name == null || engine.getClass().getName().equals(name)) {
                return engine;
            }
        }
        throw new NoSuchElementException(name);
    }
}
