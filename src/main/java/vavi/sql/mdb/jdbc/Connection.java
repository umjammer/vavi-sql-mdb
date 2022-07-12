/*
 * Copyright (c) 2004 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql.mdb.jdbc;

import java.io.IOException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Level;

import vavi.apps.mdbtools.MdbFile;
import vavi.util.Debug;


/**
 * Connection.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040620 nsano initial version <br>
 */
public class Connection implements java.sql.Connection {

    /** */
    private boolean connected = false;

    /** */
    private boolean connectionClosed = true;

    /**
     * 自動コミットを行うかどうか。デフォルトは ON
     */
    private boolean autoCommit = true;

    private String url;

    MdbFile mdb;

    @Override
    public java.sql.Statement createStatement() throws SQLException {
        return new Statement(this);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql) throws SQLException {

        throw new UnsupportedOperationException("Not implemented.");
    }

    /** TODO */
    public java.sql.PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {

        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public java.sql.CallableStatement prepareCall(String sql) throws SQLException {

        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return sql;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this.autoCommit = autoCommit;
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return autoCommit;
    }

    /** TODO */
    @Override
    public void commit() throws SQLException {
    }

    /** TODO */
    @Override
    public void rollback() throws SQLException {
    }

    /**
     * 
     */
    public void connect(String url, Properties props) throws SQLException {

        // 念のため2度目の入りを阻止します。
        if (connected) {
            return;
        }

        this.connected = true;
        this.connectionClosed = false;

        this.url = url;
        String filename = url.substring(Driver.SCHEMA.length());
Debug.println(Level.FINE, "url: " + url + ", " + filename);

        try {
            this.mdb = new MdbFile(filename);
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void close() throws SQLException {
        this.connectionClosed = true;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return connectionClosed;
    }

    @Override
    public java.sql.DatabaseMetaData getMetaData() throws SQLException {
        return new DatabaseMetaData(url);
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getCatalog() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
    }

    @Override
    public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Savepoint setSavepoint(String name) {
        return null;
    }

    /**
     * @see java.sql.Connection#getHoldability()
     */
    public int getHoldability() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Connection#setHoldability(int)
     */
    public void setHoldability(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Connection#setSavepoint()
     */
    public Savepoint setSavepoint() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
     */
    public void releaseSavepoint(Savepoint arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Connection#rollback(java.sql.Savepoint)
     */
    public void rollback(Savepoint arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * @see java.sql.Connection#createStatement(int, int, int)
     */
    public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
     */
    public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.Connection#prepareStatement(java.lang.String, int)
     */
    public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
     */
    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
     */
    public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.Connection#createArrayOf(java.lang.String, java.lang.Object[]) */
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.Connection#createBlob() */
    public Blob createBlob() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.Connection#createClob() */
    public Clob createClob() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.Connection#createNClob() */
    public NClob createNClob() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.Connection#createSQLXML() */
    public SQLXML createSQLXML() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.Connection#createStruct(java.lang.String, java.lang.Object[]) */
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.Connection#getClientInfo() */
    public Properties getClientInfo() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.Connection#getClientInfo(java.lang.String) */
    public String getClientInfo(String name) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.Connection#isValid(int) */
    public boolean isValid(int timeout) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /* @see java.sql.Connection#setClientInfo(java.util.Properties) */
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        // TODO Auto-generated method stub
        
    }

    /* @see java.sql.Connection#setClientInfo(java.lang.String, java.lang.String) */
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
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

    /* @see java.sql.Connection#setSchema(java.lang.String) */
    @Override
    public void setSchema(String schema) throws SQLException {
        // TODO Auto-generated method stub
    }

    /* @see java.sql.Connection#getSchema() */
    @Override
    public String getSchema() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.Connection#abort(java.util.concurrent.Executor) */
    @Override
    public void abort(Executor executor) throws SQLException {
        // TODO Auto-generated method stub
    }

    /* @see java.sql.Connection#setNetworkTimeout(java.util.concurrent.Executor, int) */
    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        // TODO Auto-generated method stub
    }

    /* @see java.sql.Connection#getNetworkTimeout() */
    @Override
    public int getNetworkTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }
}

/* */
