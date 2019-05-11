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
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


/**
 * ResultSet.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040620 nsano initial version <br>
 */
public class ResultSet implements java.sql.ResultSet {

    /** */
    List<Object[]> valueList = null;

    /** */
    boolean opened = false;

    int index = -1;

    /** */
    public ResultSet(List<Object[]> valueList) {
        this.valueList = valueList;
        this.opened = true;
    }

    /* */
    public boolean next() throws SQLException {
        return index++ < valueList.size() - 1;
    }

    /* */
    public void close() throws SQLException {
        this.opened = false;
    }

    /** */
    public boolean wasNull() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /* */
    public String getString(int columnIndex) throws SQLException {
        if (opened) {
            return (String) valueList.get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    /** */
    public boolean getBoolean(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public byte getByte(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public short getShort(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /* */
    public int getInt(int columnIndex) throws SQLException {
        if (opened) {
            return (Integer) valueList.get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    /** */
    public long getLong(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public float getFloat(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public double getDouble(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @deprecated
     */
    public BigDecimal getBigDecimal(int columnIndex, int scale)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public byte[] getBytes(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public java.sql.Date getDate(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public java.sql.Time getTime(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public java.sql.Timestamp getTimestamp(int columnIndex)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public java.io.InputStream getAsciiStream(int columnIndex)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @deprecated
     */
    public java.io.InputStream getUnicodeStream(int columnIndex)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public java.io.InputStream getBinaryStream(int columnIndex)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getString(String columnName) throws SQLException {
        java.sql.ResultSetMetaData rsmd = getMetaData();

        for (int index = 1; index < 1024; index++) {
            String strName = rsmd.getColumnName(index);

            if (strName.compareToIgnoreCase(columnName) == 0) {
                return getString(index);
            }
        }

        throw new SQLException("not found column: " + columnName);
    }

    /** */
    public boolean getBoolean(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public byte getByte(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public short getShort(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getInt(String columnName) throws SQLException {
        java.sql.ResultSetMetaData rsmd = getMetaData();

        for (int index = 1; index < 1024; index++) {
            String strName = rsmd.getColumnName(index);

            if (strName.compareToIgnoreCase(columnName) == 0) {
                return getInt(index);
            }
        }

        throw new SQLException("not found column: " + columnName);
    }

    /** */
    public long getLong(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public float getFloat(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public double getDouble(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @deprecated
     */
    public BigDecimal getBigDecimal(String columnName, int scale)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public byte[] getBytes(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Date getDate(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Time getTime(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Timestamp getTimestamp(String columnName)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public InputStream getAsciiStream(String columnName)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @deprecated
     */
    public InputStream getUnicodeStream(String columnName)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public InputStream getBinaryStream(String columnName)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getCursorName() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public java.sql.ResultSetMetaData getMetaData() throws SQLException {
        return new ResultSetMetaData(this);
    }

    /** */
    public Object getObject(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Object getObject(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int findColumn(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Reader getCharacterStream(int columnIndex)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Reader getCharacterStream(String columnName)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public BigDecimal getBigDecimal(String columnName)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean isBeforeFirst() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean isAfterLast() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean isFirst() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean isLast() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void beforeFirst() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void afterLast() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean first() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean last() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean absolute(int row) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean relative(int rows) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean previous() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setFetchDirection(int direction) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getFetchDirection() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void setFetchSize(int rows) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getFetchSize() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getType() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getConcurrency() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean rowUpdated() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean rowInserted() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean rowDeleted() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateNull(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateBoolean(int columnIndex, boolean x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateByte(int columnIndex, byte x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateShort(int columnIndex, short x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateInt(int columnIndex, int x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateLong(int columnIndex, long x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateFloat(int columnIndex, float x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateDouble(int columnIndex, double x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateBigDecimal(int columnIndex, BigDecimal x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateString(int columnIndex, String x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateBytes(int columnIndex, byte[] x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateDate(int columnIndex, java.sql.Date x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateTime(int columnIndex, java.sql.Time x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateTimestamp(int columnIndex, java.sql.Timestamp x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateAsciiStream(int columnIndex, java.io.InputStream x,
                                  int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateBinaryStream(int columnIndex, java.io.InputStream x,
                                   int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateCharacterStream(int columnIndex, java.io.Reader x,
                                      int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateObject(int columnIndex, Object x, int scale)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateObject(int columnIndex, Object x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateNull(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateBoolean(String columnName, boolean x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateByte(String columnName, byte x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateShort(String columnName, short x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateInt(String columnName, int x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateLong(String columnName, long x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateFloat(String columnName, float x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateDouble(String columnName, double x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateBigDecimal(String columnName, BigDecimal x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateString(String columnName, String x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateBytes(String columnName, byte[] x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateDate(String columnName, java.sql.Date x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateTime(String columnName, java.sql.Time x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateTimestamp(String columnName, java.sql.Timestamp x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateAsciiStream(String columnName, InputStream x,
                                  int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateBinaryStream(String columnName, java.io.InputStream x,
                                   int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateCharacterStream(String columnName, java.io.Reader reader,
                                      int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateObject(String columnName, Object x, int scale)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateObject(String columnName, Object x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void insertRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void updateRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void deleteRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void refreshRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void cancelRowUpdates() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void moveToInsertRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void moveToCurrentRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public java.sql.Statement getStatement() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Ref getRef(int i) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Blob getBlob(int i) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Clob getClob(int i) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Array getArray(int i) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Object getObject(String colName, Map<String, Class<?>> map)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Ref getRef(String colName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Blob getBlob(String colName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Clob getClob(String colName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Array getArray(String colName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Date getDate(int columnIndex, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Date getDate(String columnName, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Time getTime(int columnIndex, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Time getTime(String columnName, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Timestamp getTimestamp(int columnIndex, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public Timestamp getTimestamp(String columnName, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @throws SQLException */
    public void updateRef(String columnName, Ref value) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @throws SQLException */
    public void updateClob(String columnName, Clob value) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @see java.sql.ResultSet#getURL(int)
     */
    public URL getURL(int arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.ResultSet#updateArray(int, java.sql.Array)
     */
    public void updateArray(int arg0, Array arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.ResultSet#updateBlob(int, java.sql.Blob)
     */
    public void updateBlob(int arg0, Blob arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.ResultSet#updateClob(int, java.sql.Clob)
     */
    public void updateClob(int arg0, Clob arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.ResultSet#updateRef(int, java.sql.Ref)
     */
    public void updateRef(int arg0, Ref arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.ResultSet#getURL(java.lang.String)
     */
    public URL getURL(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.ResultSet#updateArray(java.lang.String, java.sql.Array)
     */
    public void updateArray(String arg0, Array arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.ResultSet#updateBlob(java.lang.String, java.sql.Blob)
     */
    public void updateBlob(String arg0, Blob arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#getHoldability() */
    public int getHoldability() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /* @see java.sql.ResultSet#getNCharacterStream(int) */
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.ResultSet#getNCharacterStream(java.lang.String) */
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.ResultSet#getNClob(int) */
    public NClob getNClob(int columnIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.ResultSet#getNClob(java.lang.String) */
    public NClob getNClob(String columnLabel) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.ResultSet#getNString(int) */
    public String getNString(int columnIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.ResultSet#getNString(java.lang.String) */
    public String getNString(String columnLabel) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.ResultSet#getRowId(int) */
    public RowId getRowId(int columnIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.ResultSet#getRowId(java.lang.String) */
    public RowId getRowId(String columnLabel) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.ResultSet#getSQLXML(int) */
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.ResultSet#getSQLXML(java.lang.String) */
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.ResultSet#isClosed() */
    public boolean isClosed() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /* @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream) */
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateAsciiStream(java.lang.String, java.io.InputStream) */
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream, long) */
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateAsciiStream(java.lang.String, java.io.InputStream, long) */
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream) */
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateBinaryStream(java.lang.String, java.io.InputStream) */
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream, long) */
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateBinaryStream(java.lang.String, java.io.InputStream, long) */
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateBlob(int, java.io.InputStream) */
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateBlob(java.lang.String, java.io.InputStream) */
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateBlob(int, java.io.InputStream, long) */
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateBlob(java.lang.String, java.io.InputStream, long) */
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader) */
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateCharacterStream(java.lang.String, java.io.Reader) */
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader, long) */
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateCharacterStream(java.lang.String, java.io.Reader, long) */
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateClob(int, java.io.Reader) */
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateClob(java.lang.String, java.io.Reader) */
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateClob(int, java.io.Reader, long) */
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateClob(java.lang.String, java.io.Reader, long) */
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateNCharacterStream(int, java.io.Reader) */
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateNCharacterStream(java.lang.String, java.io.Reader) */
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateNCharacterStream(int, java.io.Reader, long) */
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateNCharacterStream(java.lang.String, java.io.Reader, long) */
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateNClob(int, java.sql.NClob) */
    public void updateNClob(int columnIndex, NClob clob) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateNClob(java.lang.String, java.sql.NClob) */
    public void updateNClob(String columnLabel, NClob clob) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateNClob(int, java.io.Reader) */
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateNClob(java.lang.String, java.io.Reader) */
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateNClob(int, java.io.Reader, long) */
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateNClob(java.lang.String, java.io.Reader, long) */
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateNString(int, java.lang.String) */
    public void updateNString(int columnIndex, String string) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateNString(java.lang.String, java.lang.String) */
    public void updateNString(String columnLabel, String string) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateRowId(int, java.sql.RowId) */
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateRowId(java.lang.String, java.sql.RowId) */
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateSQLXML(int, java.sql.SQLXML) */
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        // TODO Auto-generated method stub

    }

    /* @see java.sql.ResultSet#updateSQLXML(java.lang.String, java.sql.SQLXML) */
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
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

    /* @see java.sql.ResultSet#getObject(int, java.lang.Class) */
    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.ResultSet#getObject(java.lang.String, java.lang.Class) */
    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }
}

/* */
