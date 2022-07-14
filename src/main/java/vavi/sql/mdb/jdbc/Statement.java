/*
 * Copyright (c) 2004 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql.mdb.jdbc;

import java.sql.SQLException;
import java.sql.SQLWarning;

import vavi.apps.mdbtools.MdbFile;


/**
 * Statement.
 * 
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040620 nsano initial version <br>
 */
public class Statement implements java.sql.Statement {

    /** */
    private Engine engine = null;

    /** */
    private java.sql.ResultSet currentResultSet = null;

    /** */
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    /** */
    public Statement(Engine engine) {
        this.engine = engine;
    }

    @Override
    protected void finalize() {
        try {
            close();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public java.sql.ResultSet executeQuery(String sql) throws SQLException {
        if (!execute(sql)) {
            return null;
        }

        currentResultSet = new ResultSet(engine.getValues());

        return currentResultSet;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        int count = -1;

        if (execute(sql)) {
            count = getUpdateCount();
        }

        return count;
    }

    /** TODO */
    @Override
    public int executeUpdate(String sql, String[] columnNmaes)
        throws SQLException {

        int count = -1;

        if (execute(sql)) {
            count = getUpdateCount();
        }

        return count;
    }

    @Override
    public void close() throws SQLException {
        if (currentResultSet != null) {
            currentResultSet.close();
            currentResultSet = null;
        }
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
            throw new UnsupportedOperationException("Not implemented.");
        }

    @Override
    public int getMaxRows() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
            throw new UnsupportedOperationException("Not implemented.");
        }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        if (seconds != 0) {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    @Override
    public void cancel() throws SQLException {
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
    public void setCursorName(String name) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return engine.excute(sql);
    }

    @Override
    public boolean execute(String sql, String[] x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean execute(String sql, int[] x) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public java.sql.ResultSet getResultSet() throws SQLException {
        return currentResultSet;
    }

    /** TODO */
    @Override
    public int getUpdateCount() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
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
    public int getResultSetConcurrency() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getResultSetType() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void clearBatch() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int[] executeBatch() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public java.sql.Connection getConnection() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public java.sql.ResultSet getGeneratedKeys() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean getMoreResults(int arg0) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int executeUpdate(String arg0, int arg1) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean execute(String arg0, int arg1) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int executeUpdate(String arg0, int[] arg1) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isClosed() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
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
    public void closeOnCompletion() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }
}

/* */
