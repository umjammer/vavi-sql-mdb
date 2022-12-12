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
import java.util.Map;
import java.util.logging.Level;

import vavi.sql.ResultSettable;
import vavi.util.Debug;


/**
 * ResultSet.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040620 nsano initial version <br>
 */
public class ResultSet implements java.sql.ResultSet {

    /** */
    ResultSettable resultSettable;

    /** */
    boolean opened;

    /** */
    int index = -1;

    /** */
    public ResultSet(ResultSettable resultSettable) {
        this.resultSettable = resultSettable;
        this.opened = true;
    }

    @Override
    public boolean next() throws SQLException {
        if (index + 1 <= resultSettable.getValues().size() - 1) {
            index++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void close() throws SQLException {
        this.opened = false;
    }

    @Override
    public boolean wasNull() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        if (opened) {
            return (String) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        if (opened) {
            return (Boolean) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        if (opened) {
            return (Byte) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        if (opened) {
            return (Short) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        if (opened) {
            return (Integer) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        if (opened) {
            return (Long) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        if (opened) {
            return (Float) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        if (opened) {
            return (Double) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    /**
     * @deprecated
     */
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        if (opened) {
            return (BigDecimal) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        if (opened) {
            return (byte[]) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public java.sql.Date getDate(int columnIndex) throws SQLException {
        if (opened) {
            return (java.sql.Date) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public java.sql.Time getTime(int columnIndex) throws SQLException {
        if (opened) {
            return (java.sql.Time) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public java.sql.Timestamp getTimestamp(int columnIndex) throws SQLException {
        if (opened) {
            return (java.sql.Timestamp) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public java.io.InputStream getAsciiStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @deprecated
     */
    public java.io.InputStream getUnicodeStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public java.io.InputStream getBinaryStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    private int indexOf(String columnName) throws SQLException {
        java.sql.ResultSetMetaData rsmd = getMetaData();

        for (int index = 1; index <= rsmd.getColumnCount(); index++) {
            String strName = rsmd.getColumnName(index);

            if (strName.equalsIgnoreCase(columnName)) {
                return index;
            }
        }
Debug.println(Level.WARNING, columnName);
        return -1;
    }

    @Override
    public String getString(String columnName) throws SQLException {
        int index = indexOf(columnName);
        if (index > 0) {
            return getString(index);
        } else {
            throw new SQLException("not found column: " + columnName);
        }
    }

    @Override
    public boolean getBoolean(String columnName) throws SQLException {
        int index = indexOf(columnName);
        if (index > 0) {
            return getBoolean(index);
        } else {
            throw new SQLException("not found column: " + columnName);
        }
    }

    @Override
    public byte getByte(String columnName) throws SQLException {
        int index = indexOf(columnName);
        if (index > 0) {
            return getByte(index);
        } else {
            throw new SQLException("not found column: " + columnName);
        }
    }

    @Override
    public short getShort(String columnName) throws SQLException {
        int index = indexOf(columnName);
        if (index > 0) {
            return getShort(index);
        } else {
            throw new SQLException("not found column: " + columnName);
        }
    }

    @Override
    public int getInt(String columnName) throws SQLException {
        int index = indexOf(columnName);
        if (index > 0) {
            return getInt(index);
        } else {
            throw new SQLException("not found column: " + columnName);
        }
    }

    @Override
    public long getLong(String columnName) throws SQLException {
        int index = indexOf(columnName);
        if (index > 0) {
            return getLong(index);
        } else {
            throw new SQLException("not found column: " + columnName);
        }
    }

    @Override
    public float getFloat(String columnName) throws SQLException {
        int index = indexOf(columnName);
        if (index > 0) {
            return getFloat(index);
        } else {
            throw new SQLException("not found column: " + columnName);
        }
    }

    @Override
    public double getDouble(String columnName) throws SQLException {
        int index = indexOf(columnName);
        if (index > 0) {
            return getDouble(index);
        } else {
            throw new SQLException("not found column: " + columnName);
        }
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        if (opened) {
            return resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public Object getObject(String columnName) throws SQLException {
        int index = indexOf(columnName);
        if (index > 0) {
            return getObject(index);
        } else {
            throw new SQLException("not found column: " + columnName);
        }
    }

    /**
     * @deprecated
     */
    public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public byte[] getBytes(String columnName) throws SQLException {
        int index = indexOf(columnName);
        if (index > 0) {
            return getBytes(index);
        } else {
            throw new SQLException("not found column: " + columnName);
        }
    }

    @Override
    public Date getDate(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Time getTime(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Timestamp getTimestamp(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public InputStream getAsciiStream(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @deprecated
     */
    public InputStream getUnicodeStream(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public InputStream getBinaryStream(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getCursorName() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public java.sql.ResultSetMetaData getMetaData() throws SQLException {
        return new ResultSetMetaData(this.resultSettable);
    }

    @Override
    public int findColumn(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Reader getCharacterStream(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        if (opened) {
            return (BigDecimal) resultSettable.getValues().get(index)[columnIndex - 1];
        } else {
            throw new SQLException("closed");
        }
    }

    @Override
    public BigDecimal getBigDecimal(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isFirst() throws SQLException {
        return index == 0;
    }

    @Override
    public boolean isLast() throws SQLException {
        return index == resultSettable.getValues().size() - 1;
    }

    @Override
    public void beforeFirst() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void afterLast() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean first() throws SQLException {
        index = 0;
        return true;
    }

    @Override
    public boolean last() throws SQLException {
        index = resultSettable.getValues().size() - 1;
        return true;
    }

    @Override
    public int getRow() throws SQLException {
        return index;
    }

    @Override
    public boolean absolute(int row) throws SQLException {
        if (0 <= row && row <= resultSettable.getValues().size() - 1) {
            index = row;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean relative(int rows) throws SQLException {
        if (0 <= index + rows && index + rows <= resultSettable.getValues().size() - 1) {
            index += rows;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean previous() throws SQLException {
        if (index - 1 >= 0) {
            index--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return ResultSet.FETCH_FORWARD;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getType() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getConcurrency() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean rowInserted() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBoolean(int columnIndex, boolean x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateDouble(int columnIndex, double x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateString(int columnIndex, String x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBytes(int columnIndex, byte[] x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateDate(int columnIndex, java.sql.Date x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateTime(int columnIndex, java.sql.Time x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateTimestamp(int columnIndex, java.sql.Timestamp x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateAsciiStream(int columnIndex, java.io.InputStream x,
                                  int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBinaryStream(int columnIndex, java.io.InputStream x,
                                   int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateCharacterStream(int columnIndex, java.io.Reader x,
                                      int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateObject(int columnIndex, Object x, int scale)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateObject(int columnIndex, Object x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNull(String columnName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBoolean(String columnName, boolean x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateByte(String columnName, byte x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateShort(String columnName, short x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateInt(String columnName, int x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateLong(String columnName, long x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateFloat(String columnName, float x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateDouble(String columnName, double x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBigDecimal(String columnName, BigDecimal x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateString(String columnName, String x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBytes(String columnName, byte[] x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateDate(String columnName, java.sql.Date x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateTime(String columnName, java.sql.Time x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateTimestamp(String columnName, java.sql.Timestamp x)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBinaryStream(String columnName, java.io.InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateCharacterStream(String columnName, java.io.Reader reader, int length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateObject(String columnName, Object x, int scale) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateObject(String columnName, Object x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void insertRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void deleteRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void refreshRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public java.sql.Statement getStatement() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Ref getRef(int i) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Blob getBlob(int i) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Clob getClob(int i) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Array getArray(int i) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Object getObject(String colName, Map<String, Class<?>> map)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Ref getRef(String colName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Blob getBlob(String colName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Clob getClob(String colName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Array getArray(String colName) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Date getDate(String columnName, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Time getTime(String columnName, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Timestamp getTimestamp(String columnName, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateRef(String columnName, Ref value) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateClob(String columnName, Clob value) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public URL getURL(int arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateArray(int arg0, Array arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateBlob(int arg0, Blob arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateClob(int arg0, Clob arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateRef(int arg0, Ref arg1) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public URL getURL(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateArray(String arg0, Array arg1) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBlob(String arg0, Blob arg1) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getNString(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getNString(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isClosed() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNClob(int columnIndex, NClob clob) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNClob(String columnLabel, NClob clob) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNString(int columnIndex, String string) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateNString(String columnLabel, String string) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }
}

/* */
