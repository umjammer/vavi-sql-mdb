/*
 * Copyright (c) 2004 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql.mdb.jdbc;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Struct;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import vavi.apps.mdbtools.MdbFile;
import vavi.apps.mdbtools.Table;
import vavi.sql.Engine;
import vavi.sql.ResultSettable;

import static java.lang.System.getLogger;


/**
 * Connection.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040620 nsano initial version <br>
 */
public class Connection implements java.sql.Connection {

    private static final Logger logger = getLogger(Connection.class.getName());

    /** */
    private boolean connected = false;

    /** */
    private boolean connectionClosed = true;

    /** */
    private boolean autoCommit = true;

    /** url for this database */
    private String url;

    /** real database */
    private MdbFile mdb;

    /** */
    public Engine engine() {
        Engine engine = Engine.factory(null);
        engine.setDatabase(new ResultSettable() {
            private String table;

            @Override
            public void setTable(String table) {
                this.table = table;
            }

            @Override
            public List<Object[]> getValues() {
                try {
                    Table table = mdb.getTable(this.table);
                    return table.fetchRows();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        });
        engine.setDatabaseMetaData(getMetaData());
        return engine;
    }

    @Override
    public java.sql.Statement createStatement() throws SQLException {
        return new Statement(this);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql) throws SQLException {
        return new PreparedStatement(this, sql);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return new PreparedStatement(this, sql, columnNames);
    }

    @Override
    public java.sql.CallableStatement prepareCall(String sql) throws SQLException {
        return new CallableStatement(this, sql);
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

    @Override
    public void commit() throws SQLException {
        // TODO
    }

    @Override
    public void rollback() throws SQLException {
        // TODO
    }

    /**
     * connect to the real database.
     */
    public void connect(String url, Properties props) throws SQLException {

        // prevent a second entry
        if (connected) {
            return;
        }

        this.connected = true;
        this.connectionClosed = false;

        this.url = url;
        String filename = url.substring(Driver.SCHEMA.length());
logger.log(Level.DEBUG, "url: " + url + ", " + filename);

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
    public java.sql.DatabaseMetaData getMetaData() {
        return new DatabaseMetaData(url, mdb);
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

    @Override
    public int getHoldability() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setHoldability(int arg0) throws SQLException {
        // TODO Auto-generated method stub
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void releaseSavepoint(Savepoint arg0) throws SQLException {
        // TODO Auto-generated method stub
    }

    @Override
    public void rollback(Savepoint arg0) throws SQLException {
        // TODO Auto-generated method stub
    }

    @Override
    public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        // TODO Auto-generated method stub
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
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
    public void setSchema(String schema) throws SQLException {
        // TODO Auto-generated method stub
    }

    @Override
    public String getSchema() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        // TODO Auto-generated method stub
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        // TODO Auto-generated method stub
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }
}
