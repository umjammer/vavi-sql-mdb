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
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;


/**
 * CallableStatement.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040620 nsano initial version <br>
 */
public class CallableStatement
    implements java.sql.CallableStatement {

    /** */
    public void registerOutParameter(int parameterIndex, int sqlType)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void registerOutParameter(int parameterIndex, int sqlType, int scale)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean wasNull() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getString(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean getBoolean(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public byte getByte(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public short getShort(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getInt(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public long getLong(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public float getFloat(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public double getDouble(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @deprecated
     */
    public BigDecimal getBigDecimal(int parameterIndex, int scale)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public byte[] getBytes(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Date getDate(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Time getTime(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Timestamp getTimestamp(int parameterIndex)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Object getObject(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setObject(int parameterIndex, Object x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public BigDecimal getBigDecimal(int parameterIndex)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Ref getRef(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Blob getBlob(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Clob getClob(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Array getArray(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setArray(int parameterIndex, Array x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Date getDate(int parameterIndex, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setDate(int parameterIndex, Date x, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Time getTime(int parameterIndex, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setTime(int parameterIndex, Time x, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Timestamp getTimestamp(int parameterIndex, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setTimestamp(String columnName, Timestamp x, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void registerOutParameter(int parameterIndex,
                                     int sqlType,
                                     String typeName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @see java.sql.CallableStatement#getByte(java.lang.String)
     */
    public byte getByte(String name) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.CallableStatement#getDouble(java.lang.String)
     */
    public double getDouble(String name) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.CallableStatement#getFloat(java.lang.String)
     */
    public float getFloat(String name) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.CallableStatement#getInt(java.lang.String)
     */
    public int getInt(String name) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.CallableStatement#getLong(java.lang.String)
     */
    public long getLong(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.CallableStatement#getShort(java.lang.String)
     */
    public short getShort(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.CallableStatement#getBoolean(java.lang.String)
     */
    public boolean getBoolean(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.CallableStatement#getBytes(java.lang.String)
     */
    public byte[] getBytes(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#setByte(java.lang.String, byte)
     */
    public void setByte(String arg0, byte arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setDouble(java.lang.String, double)
     */
    public void setDouble(String arg0, double arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setFloat(java.lang.String, float)
     */
    public void setFloat(String arg0, float arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#registerOutParameter(java.lang.String, int)
     */
    public void registerOutParameter(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setInt(java.lang.String, int)
     */
    public void setInt(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setNull(java.lang.String, int)
     */
    public void setNull(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#registerOutParameter(java.lang.String, int, int)
     */
    public void registerOutParameter(String arg0, int arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setLong(java.lang.String, long)
     */
    public void setLong(String arg0, long arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setShort(java.lang.String, short)
     */
    public void setShort(String arg0, short arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setBoolean(java.lang.String, boolean)
     */
    public void setBoolean(String arg0, boolean arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setBytes(java.lang.String, byte[])
     */
    public void setBytes(String arg0, byte[] arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#getURL(int)
     */
    public URL getURL(int arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#setAsciiStream(java.lang.String, java.io.InputStream, int)
     */
    public void setAsciiStream(String arg0, InputStream arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setBinaryStream(java.lang.String, java.io.InputStream, int)
     */
    public void setBinaryStream(String arg0, InputStream arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setCharacterStream(java.lang.String, java.io.Reader, int)
     */
    public void setCharacterStream(String arg0, Reader arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#getObject(java.lang.String)
     */
    public Object getObject(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#setObject(java.lang.String, java.lang.Object)
     */
    public void setObject(String arg0, Object arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setObject(java.lang.String, java.lang.Object, int)
     */
    public void setObject(String arg0, Object arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setObject(java.lang.String, java.lang.Object, int, int)
     */
    public void setObject(String arg0, Object arg1, int arg2, int arg3) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#getString(java.lang.String)
     */
    public String getString(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#registerOutParameter(java.lang.String, int, java.lang.String)
     */
    public void registerOutParameter(String arg0, int arg1, String arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setNull(java.lang.String, int, java.lang.String)
     */
    public void setNull(String arg0, int arg1, String arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setString(java.lang.String, java.lang.String)
     */
    public void setString(String arg0, String arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#getBigDecimal(java.lang.String)
     */
    public BigDecimal getBigDecimal(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#setBigDecimal(java.lang.String, java.math.BigDecimal)
     */
    public void setBigDecimal(String arg0, BigDecimal arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#getURL(java.lang.String)
     */
    public URL getURL(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#setURL(java.lang.String, java.net.URL)
     */
    public void setURL(String arg0, URL arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#getArray(java.lang.String)
     */
    public Array getArray(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#getBlob(java.lang.String)
     */
    public Blob getBlob(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#getClob(java.lang.String)
     */
    public Clob getClob(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#getDate(java.lang.String)
     */
    public Date getDate(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#setDate(java.lang.String, java.sql.Date)
     */
    public void setDate(String arg0, Date arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#getRef(java.lang.String)
     */
    public Ref getRef(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#getTime(java.lang.String)
     */
    public Time getTime(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#setTime(java.lang.String, java.sql.Time)
     */
    public void setTime(String arg0, Time arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#getTimestamp(java.lang.String)
     */
    public Timestamp getTimestamp(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#setTimestamp(java.lang.String, java.sql.Timestamp)
     */
    public void setTimestamp(String arg0, Timestamp arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#getObject(java.lang.String, java.util.Map)
     */
    public Object getObject(String arg0, Map<String, Class<?>> arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#getDate(java.lang.String, java.util.Calendar)
     */
    public Date getDate(String arg0, Calendar arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#getTime(java.lang.String, java.util.Calendar)
     */
    public Time getTime(String arg0, Calendar arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#getTimestamp(java.lang.String, java.util.Calendar)
     */
    public Timestamp getTimestamp(String arg0, Calendar arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.CallableStatement#setDate(java.lang.String, java.sql.Date, java.util.Calendar)
     */
    public void setDate(String arg0, Date arg1, Calendar arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.CallableStatement#setTime(java.lang.String, java.sql.Time, java.util.Calendar)
     */
    public void setTime(String arg0, Time arg1, Calendar arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#executeUpdate()
     */
    public int executeUpdate() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.PreparedStatement#addBatch()
     */
    public void addBatch() throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#clearParameters()
     */
    public void clearParameters() throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#execute()
     */
    public boolean execute() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.PreparedStatement#setByte(int, byte)
     */
    public void setByte(int arg0, byte arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setDouble(int, double)
     */
    public void setDouble(int arg0, double arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setFloat(int, float)
     */
    public void setFloat(int arg0, float arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setInt(int, int)
     */
    public void setInt(int arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setNull(int, int)
     */
    public void setNull(int arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setLong(int, long)
     */
    public void setLong(int arg0, long arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setShort(int, short)
     */
    public void setShort(int arg0, short arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setBoolean(int, boolean)
     */
    public void setBoolean(int arg0, boolean arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setBytes(int, byte[])
     */
    public void setBytes(int arg0, byte[] arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream, int)
     */
    public void setAsciiStream(int arg0, InputStream arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream, int)
     */
    public void setBinaryStream(int arg0, InputStream arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setUnicodeStream(int, java.io.InputStream, int)
     */
    public void setUnicodeStream(int arg0, InputStream arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader, int)
     */
    public void setCharacterStream(int arg0, Reader arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setObject(int, java.lang.Object, int)
     */
    public void setObject(int arg0, Object arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setObject(int, java.lang.Object, int, int)
     */
    public void setObject(int arg0, Object arg1, int arg2, int arg3) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setNull(int, int, java.lang.String)
     */
    public void setNull(int arg0, int arg1, String arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setString(int, java.lang.String)
     */
    public void setString(int arg0, String arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setBigDecimal(int, java.math.BigDecimal)
     */
    public void setBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setURL(int, java.net.URL)
     */
    public void setURL(int arg0, URL arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setBlob(int, java.sql.Blob)
     */
    public void setBlob(int arg0, Blob arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setClob(int, java.sql.Clob)
     */
    public void setClob(int arg0, Clob arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setDate(int, java.sql.Date)
     */
    public void setDate(int arg0, Date arg1) throws SQLException {
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
     * @see java.sql.PreparedStatement#setRef(int, java.sql.Ref)
     */
    public void setRef(int arg0, Ref arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#executeQuery()
     */
    public ResultSet executeQuery() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.PreparedStatement#getMetaData()
     */
    public ResultSetMetaData getMetaData() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.PreparedStatement#setTime(int, java.sql.Time)
     */
    public void setTime(int arg0, Time arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp)
     */
    public void setTimestamp(int arg0, Timestamp arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp, java.util.Calendar)
     */
    public void setTimestamp(int arg0, Timestamp arg1, Calendar arg2) throws SQLException {
        // TODO Auto-generated method stub

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

    /* @see java.sql.CallableStatement#getCharacterStream(int) */
    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#getCharacterStream(java.lang.String) */
    public Reader getCharacterStream(String parameterName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#getNCharacterStream(int) */
    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#getNCharacterStream(java.lang.String) */
    public Reader getNCharacterStream(String parameterName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#getNClob(int) */
    public NClob getNClob(int parameterIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#getNClob(java.lang.String) */
    public NClob getNClob(String parameterName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#getNString(int) */
    public String getNString(int parameterIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#getNString(java.lang.String) */
    public String getNString(String parameterName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#getRowId(int) */
    public RowId getRowId(int parameterIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#getRowId(java.lang.String) */
    public RowId getRowId(String parameterName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#getSQLXML(int) */
    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#getSQLXML(java.lang.String) */
    public SQLXML getSQLXML(String parameterName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#setAsciiStream(java.lang.String, java.io.InputStream) */
    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setAsciiStream(java.lang.String, java.io.InputStream, long) */
    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setBinaryStream(java.lang.String, java.io.InputStream) */
    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setBinaryStream(java.lang.String, java.io.InputStream, long) */
    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setBlob(java.lang.String, java.sql.Blob) */
    public void setBlob(String parameterName, Blob x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setBlob(java.lang.String, java.io.InputStream) */
    public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setBlob(java.lang.String, java.io.InputStream, long) */
    public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setCharacterStream(java.lang.String, java.io.Reader) */
    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setCharacterStream(java.lang.String, java.io.Reader, long) */
    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setClob(java.lang.String, java.sql.Clob) */
    public void setClob(String parameterName, Clob x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setClob(java.lang.String, java.io.Reader) */
    public void setClob(String parameterName, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setClob(java.lang.String, java.io.Reader, long) */
    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setNCharacterStream(java.lang.String, java.io.Reader) */
    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setNCharacterStream(java.lang.String, java.io.Reader, long) */
    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setNClob(java.lang.String, java.sql.NClob) */
    public void setNClob(String parameterName, NClob value) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setNClob(java.lang.String, java.io.Reader) */
    public void setNClob(String parameterName, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setNClob(java.lang.String, java.io.Reader, long) */
    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setNString(java.lang.String, java.lang.String) */
    public void setNString(String parameterName, String value) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setRowId(java.lang.String, java.sql.RowId) */
    public void setRowId(String parameterName, RowId x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.CallableStatement#setSQLXML(java.lang.String, java.sql.SQLXML) */
    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        // TODO Auto-generated method stub

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
    public void closeOnCompletion() throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.Statement#isCloseOnCompletion() */
    public boolean isCloseOnCompletion() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /* @see java.sql.CallableStatement#getObject(int, java.lang.Class) */
    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.CallableStatement#getObject(java.lang.String, java.lang.Class) */
    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }
}

/* */
