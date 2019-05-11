/*
 * Copyright (c) 2004 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql.mdb.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * PreparedStatement.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040620 nsano initial version <br>
 */
public class PreparedStatement
    implements java.sql.PreparedStatement {

    /** */
    List<Object> vecParam = new ArrayList<>();

    /** */
    private ResultSet currentResultSet;

    /** */
    private Connection connection;

    /** */
    private String sql;

    /** */
    public PreparedStatement(Connection connection, String sql) {
        this.connection = connection;
        this.sql = sql;
    }

    /** */
    public java.sql.ResultSet executeQuery() throws SQLException {
        if (execute() == false) {
            return null;
        }

        this.currentResultSet = this.getResultSet();

        return currentResultSet;
    }

    /** TODO */
    public int executeUpdate() throws SQLException {
        return 0;
    }

    /** */
    public void setNull(int parameterIndex, int sqlType)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setBoolean(int parameterIndex, boolean x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setByte(int parameterIndex, byte x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setShort(int parameterIndex, short x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setInt(int parameterIndex, int x) throws SQLException {
        // かなり乱暴な対応方法
        if (parameterIndex > vecParam.size()) {
            vecParam.add(new Integer(x));
        } else {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    /** */
    public void setLong(int parameterIndex, long x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setFloat(int parameterIndex, float x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setDouble(int parameterIndex, double x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setBigDecimal(int parameterIndex, BigDecimal x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setString(int parameterIndex, String x)
        throws SQLException {
        if (parameterIndex > vecParam.size()) {
            vecParam.add(x);
        } else {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    /** */
    public void setBytes(int parameterIndex, byte[] x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setDate(int parameterIndex, Date x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setTime(int parameterIndex,
                        Time x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setTimestamp(int parameterIndex,
                             Timestamp x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setAsciiStream(int parameterIndex,
                               InputStream x,
                               int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @deprecated
     */
    public void setUnicodeStream(int parameterIndex, InputStream x,
                                 int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setBinaryStream(int parameterIndex, java.io.InputStream x,
                                int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void clearParameters() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType,
                          int scale) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setObject(int parameterIndex, Object x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public boolean execute() throws SQLException {
        return false;
    }

    public void addBatch() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setCharacterStream(int parameterIndex,
                                   Reader reader,
                                   int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setRef(int i, Ref x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setBlob(int i, Blob x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setClob(int i, Clob x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setArray(int i, Array x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public java.sql.ResultSetMetaData getMetaData() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setDate(int parameterIndex, Date x, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setTime(int parameterIndex, Time x, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setTimestamp(int parameterIndex,
                             Timestamp x,
                             Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setNull(int paramIndex, int sqlType, String typeName)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @see java.sql.PreparedStatement#setURL(int, java.net.URL)
     */
    public void setURL(int arg0, URL arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#getParameterMetaData()
     */
    public ParameterMetaData getParameterMetaData() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.Statement#getFetchDirection()
     */
    public int getFetchDirection() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#getFetchSize()
     */
    public int getFetchSize() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#getMaxFieldSize()
     */
    public int getMaxFieldSize() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#getMaxRows()
     */
    public int getMaxRows() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#getQueryTimeout()
     */
    public int getQueryTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#getResultSetConcurrency()
     */
    public int getResultSetConcurrency() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#getResultSetHoldability()
     */
    public int getResultSetHoldability() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#getResultSetType()
     */
    public int getResultSetType() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#getUpdateCount()
     */
    public int getUpdateCount() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#cancel()
     */
    public void cancel() throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Statement#clearBatch()
     */
    public void clearBatch() throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Statement#clearWarnings()
     */
    public void clearWarnings() throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Statement#close()
     */
    public void close() throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Statement#getMoreResults()
     */
    public boolean getMoreResults() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.Statement#executeBatch()
     */
    public int[] executeBatch() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.Statement#setFetchDirection(int)
     */
    public void setFetchDirection(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Statement#setFetchSize(int)
     */
    public void setFetchSize(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Statement#setMaxFieldSize(int)
     */
    public void setMaxFieldSize(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Statement#setMaxRows(int)
     */
    public void setMaxRows(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Statement#setQueryTimeout(int)
     */
    public void setQueryTimeout(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Statement#getMoreResults(int)
     */
    public boolean getMoreResults(int arg0) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.Statement#setEscapeProcessing(boolean)
     */
    public void setEscapeProcessing(boolean arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Statement#executeUpdate(java.lang.String)
     */
    public int executeUpdate(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#addBatch(java.lang.String)
     */
    public void addBatch(String arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Statement#setCursorName(java.lang.String)
     */
    public void setCursorName(String arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Statement#execute(java.lang.String)
     */
    public boolean execute(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.Statement#executeUpdate(java.lang.String, int)
     */
    public int executeUpdate(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#execute(java.lang.String, int)
     */
    public boolean execute(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
     */
    public int executeUpdate(String arg0, int[] arg1) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#execute(java.lang.String, int[])
     */
    public boolean execute(String arg0, int[] arg1) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.Statement#getConnection()
     */
    public Connection getConnection() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.Statement#getGeneratedKeys()
     */
    public ResultSet getGeneratedKeys() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.Statement#getResultSet()
     */
    public ResultSet getResultSet() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.Statement#getWarnings()
     */
    public SQLWarning getWarnings() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.Statement#executeUpdate(java.lang.String, java.lang.String[])
     */
    public int executeUpdate(String arg0, String[] arg1) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
     */
    public boolean execute(String arg0, String[] arg1) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.Statement#executeQuery(java.lang.String)
     */
    public ResultSet executeQuery(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream) */
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream, long) */
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream) */
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream, long) */
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream) */
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream, long) */
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader) */
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader, long) */
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setClob(int, java.io.Reader) */
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setClob(int, java.io.Reader, long) */
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader) */
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader, long) */
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setNClob(int, java.sql.NClob) */
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setNClob(int, java.io.Reader) */
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setNClob(int, java.io.Reader, long) */
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setNString(int, java.lang.String) */
    public void setNString(int parameterIndex, String value) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setRowId(int, java.sql.RowId) */
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.PreparedStatement#setSQLXML(int, java.sql.SQLXML) */
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.Statement#isClosed() */
    public boolean isClosed() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /* @see java.sql.Statement#isPoolable() */
    public boolean isPoolable() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /* @see java.sql.Statement#setPoolable(boolean) */
    public void setPoolable(boolean poolable) throws SQLException {
        // TODO Auto-generated method stub

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

    /* @see java.sql.Statement#closeOnCompletion() */
    @Override
    public void closeOnCompletion() throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.Statement#isCloseOnCompletion() */
    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
}

/* */
