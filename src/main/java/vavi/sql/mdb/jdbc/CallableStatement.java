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
public class CallableStatement implements java.sql.CallableStatement {

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, int scale)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean wasNull() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getString(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean getBoolean(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public byte getByte(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public short getShort(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getInt(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public long getLong(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public float getFloat(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public double getDouble(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public BigDecimal getBigDecimal(int parameterIndex, int scale)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public byte[] getBytes(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Date getDate(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Time getTime(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Timestamp getTimestamp(int parameterIndex)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Object getObject(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setObject(int parameterIndex, Object x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public BigDecimal getBigDecimal(int parameterIndex)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Ref getRef(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Blob getBlob(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Clob getClob(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Array getArray(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void setTimestamp(String columnName, Timestamp x, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public byte getByte(String name) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getDouble(String name) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getFloat(String name) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getInt(String name) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getLong(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public short getShort(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean getBoolean(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public byte[] getBytes(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setByte(String arg0, byte arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDouble(String arg0, double arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFloat(String arg0, float arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void registerOutParameter(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setInt(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNull(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void registerOutParameter(String arg0, int arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setLong(String arg0, long arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShort(String arg0, short arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBoolean(String arg0, boolean arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBytes(String arg0, byte[] arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public URL getURL(int arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAsciiStream(String arg0, InputStream arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBinaryStream(String arg0, InputStream arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCharacterStream(String arg0, Reader arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getObject(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setObject(String arg0, Object arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setObject(String arg0, Object arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setObject(String arg0, Object arg1, int arg2, int arg3) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public String getString(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void registerOutParameter(String arg0, int arg1, String arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNull(String arg0, int arg1, String arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setString(String arg0, String arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public BigDecimal getBigDecimal(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setBigDecimal(String arg0, BigDecimal arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public URL getURL(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setURL(String arg0, URL arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public Array getArray(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Blob getBlob(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Clob getClob(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Date getDate(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDate(String arg0, Date arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public Ref getRef(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Time getTime(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTime(String arg0, Time arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public Timestamp getTimestamp(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTimestamp(String arg0, Timestamp arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getObject(String arg0, Map<String, Class<?>> arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Date getDate(String arg0, Calendar arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Time getTime(String arg0, Calendar arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Timestamp getTimestamp(String arg0, Calendar arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDate(String arg0, Date arg1, Calendar arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setTime(String arg0, Time arg1, Calendar arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public int executeUpdate() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void addBatch() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearParameters() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean execute() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setByte(int arg0, byte arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDouble(int arg0, double arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFloat(int arg0, float arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setInt(int arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNull(int arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setLong(int arg0, long arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShort(int arg0, short arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBoolean(int arg0, boolean arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBytes(int arg0, byte[] arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAsciiStream(int arg0, InputStream arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBinaryStream(int arg0, InputStream arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setUnicodeStream(int arg0, InputStream arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCharacterStream(int arg0, Reader arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setObject(int arg0, Object arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setObject(int arg0, Object arg1, int arg2, int arg3) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNull(int arg0, int arg1, String arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setString(int arg0, String arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setURL(int arg0, URL arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBlob(int arg0, Blob arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setClob(int arg0, Clob arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDate(int arg0, Date arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRef(int arg0, Ref arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTime(int arg0, Time arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setTimestamp(int arg0, Timestamp arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setTimestamp(int arg0, Timestamp arg1, Calendar arg2) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public int getFetchDirection() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getFetchSize() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMaxRows() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void cancel() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearBatch() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearWarnings() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getMoreResults() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int[] executeBatch() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setFetchDirection(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFetchSize(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setMaxFieldSize(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setMaxRows(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setQueryTimeout(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getMoreResults(int arg0) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setEscapeProcessing(boolean arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public int executeUpdate(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void addBatch(String arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCursorName(String arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean execute(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int executeUpdate(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean execute(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int executeUpdate(String arg0, int[] arg1) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean execute(String arg0, int[] arg1) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Connection getConnection() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int executeUpdate(String arg0, String[] arg1) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean execute(String arg0, String[] arg1) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ResultSet executeQuery(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Reader getCharacterStream(String parameterName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Reader getNCharacterStream(String parameterName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NClob getNClob(int parameterIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NClob getNClob(String parameterName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNString(int parameterIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNString(String parameterName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RowId getRowId(int parameterIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RowId getRowId(String parameterName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SQLXML getSQLXML(String parameterName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBlob(String parameterName, Blob x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setClob(String parameterName, Clob x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setClob(String parameterName, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNClob(String parameterName, NClob value) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNClob(String parameterName, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNString(String parameterName, String value) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRowId(String parameterName, RowId x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isClosed() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isPoolable() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        // TODO Auto-generated method stub

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

    @Override
    public void closeOnCompletion() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }
}

/* */
