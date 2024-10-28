/*
 * Copyright (c) 2004 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql.mdb.jdbc;

import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import vavi.util.Debug;


/**
 * JDBC Front End Driver.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040620 nsano initial version <br>
 */
public class Driver implements java.sql.Driver {

    // see https://blog.sgnet.co.jp/2020/03/java-jdbc.html
    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch(SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /** */
    static final int MAJOR_VERSION = 0;

    /** */
    static final int MINOR_VERSION = 3;

    /** */
    static final String SCHEMA = "jdbc:mdb:";

    @Override
    public java.sql.Connection connect(String urlString, Properties props)
        throws SQLException {

        Connection connection = new Connection();
        connection.connect(urlString, props);

        return connection;
    }

    @Override
    public boolean acceptsURL(String url) {
        return url.startsWith(SCHEMA);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String urlString, Properties props) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMajorVersion() {
        return MAJOR_VERSION;
    }

    @Override
    public int getMinorVersion() {
        return MINOR_VERSION;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getLogger(Driver.class.getName());
    }
}
