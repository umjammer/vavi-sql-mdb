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
public class ResultSetMetaData implements java.sql.ResultSetMetaData {

    /** */
    private ResultSet resultSet;

    /** */
    public ResultSetMetaData(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public int getColumnCount() throws SQLException {
        return resultSet.valueList.size();
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int isNullable(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** TODO */
    public String getColumnName(int column) throws SQLException {
        return null;
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getScale(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getTableName(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** TODO */
    public int getColumnType(int column) throws SQLException {
        return Types.OTHER;
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }
}

/* */
