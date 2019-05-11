/*
 * Copyright (c) 2004 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql.mdb.jdbc;

import java.sql.SQLException;
import java.sql.Types;


/**
 * ResultSetMetaData.
 * 
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040620 nsano initial version <br>
 */
public class ResultSetMetaData
    implements java.sql.ResultSetMetaData {

    /** */
    private java.sql.ResultSet resultSet = null;

    /** */
    public ResultSetMetaData(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /** TODO */
    public int getColumnCount() throws SQLException {
        return 0;
    }

    /** */
    public boolean isAutoIncrement(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean isCaseSensitive(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean isSearchable(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean isCurrency(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int isNullable(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean isSigned(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getColumnDisplaySize(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getColumnLabel(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** TODO */
    public String getColumnName(int column) throws SQLException {
        return null;
    }

    /** */
    public String getSchemaName(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getPrecision(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getScale(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getTableName(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getCatalogName(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** TODO */
    public int getColumnType(int column) throws SQLException {
        return Types.OTHER;
    }

    /** */
    public String getColumnTypeName(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean isReadOnly(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean isWritable(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean isDefinitelyWritable(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getColumnClassName(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /* @see java.sql.Wrapper#isWrapperFor(java.lang.Class) */
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /* @see java.sql.Wrapper#unwrap(java.lang.Class) */
    public <T> T unwrap(Class<T> iface) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }
}

/* */
