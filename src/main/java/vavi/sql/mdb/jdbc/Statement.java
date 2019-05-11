/*
 * Copyright (c) 2004 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql.mdb.jdbc;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.List;

import vavi.apps.mdbtools.MdbFile;
import vavi.apps.mdbtools.Table;
import vavi.util.Debug;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.JSqlParser;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;


/**
 * Statement.
 * 
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040620 nsano initial version <br>
 */
public class Statement
    implements java.sql.Statement {

    /** */
    private Connection connection = null;

    /** */
    private java.sql.ResultSet currentResultSet = null;

    JSqlParser parser = new CCJSqlParserManager();

    /** */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /** */
    public Statement(Connection connection) {
        this.connection = connection;
    }

    /** */
    protected void finalize() {
        try {
            close();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    /** */
    public java.sql.ResultSet executeQuery(String sql) throws SQLException {
        if (execute(sql) == false) {
            return null;
        }

        currentResultSet = new ResultSet(valuesList);

        return currentResultSet;
    }

    /** */
    public int executeUpdate(String sql) throws SQLException {
        int count = -1;

        if (execute(sql) == true) {
            count = getUpdateCount();
        }

        return count;
    }

    /** TODO */
    public int executeUpdate(String sql, String[] columnNmaes)
        throws SQLException {

        int count = -1;

        if (execute(sql) == true) {
            count = getUpdateCount();
        }

        return count;
    }

    /** */
    public void close() throws SQLException {
        if (currentResultSet != null) {
            currentResultSet.close();
            currentResultSet = null;
        }
    }

    /** */
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }

    /** */
    public void setMaxFieldSize(int max) throws SQLException {
        if (max != 0) {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    /** */
    public int getMaxRows() throws SQLException {
        return 0;
    }

    /** */
    public void setMaxRows(int max) throws SQLException {
        if (max != 0) {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    /** */
    public void setEscapeProcessing(boolean enable) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getQueryTimeout() throws SQLException {
        return 0;
    }

    /** */
    public void setQueryTimeout(int seconds) throws SQLException {
        if (seconds != 0) {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    /** */
    public void cancel() throws SQLException {
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
    public void setCursorName(String name) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean execute(String sql) throws SQLException {
        try {
            Reader reader = new StringReader(sql);
            net.sf.jsqlparser.statement.Statement statement = parser.parse(reader);
//Debug.println("statement: " + statement);

            statement.accept(statementVisitor);

            if (isSelect) {
                MdbFile mdb = connection.mdb;
                Table table = mdb.getTable(this.table);
                this.valuesList = table.fetchRows();
                return valuesList.size() != 0;
            } else {
                return false;
            }
        } catch (JSQLParserException | IOException e) {
            throw new SQLException(e);
        }
    }

    List<Object[]> valuesList;

    String table;
    boolean isSelect;
    boolean isSelectAll;

    private SelectVisitor selectVisitor = new SelectVisitorAdapter() {

        @Override
        public void visit(PlainSelect plainSelect) {
System.out.println("plainSelect\t" + plainSelect);

            FromItem from = plainSelect.getFromItem();
System.out.println("FromItem=" + from);
            table = from.toString();

            List<SelectItem> itemList = plainSelect.getSelectItems();
            for (SelectItem item : itemList) {
System.out.println("SelectItem=" + item);
                item.accept(selectItemVisitor);
            }

            Expression where = plainSelect.getWhere();
System.out.println("where=" + where);
            if (where != null) {
                where.accept(expressionVisitor);
            }
        }
    };

    StatementVisitorAdapter statementVisitor = new StatementVisitorAdapter() {
        @Override
        public void visit(Select select) {
System.out.println("select\t" + select);
            isSelect = true;
            SelectBody body = select.getSelectBody();
System.out.println("body\t" + body);
            body.accept(selectVisitor);
        }
    };

    private SelectItemVisitor selectItemVisitor = new SelectItemVisitorAdapter() {
        // *
        @Override
        public void visit(AllColumns columns) {
System.out.println("AllColumns\t" + columns);
        }

        // t.*
        @Override
        public void visit(AllTableColumns columns) {
System.out.println("AllTableColumns\t" + columns);
            isSelectAll = true;
        }

        // 通常のカラム
        @Override
        public void visit(SelectExpressionItem item) {
System.out.println("SelectExpressionItem\t" + item);

            Alias alias = item.getAlias();
System.out.println("alias=" + alias);

            Expression expression = item.getExpression();
System.out.println("expression=" + expression);

            expression.accept(expressionVisitor);
        }
    };

    private ExpressionVisitor expressionVisitor = new ExpressionVisitorAdapter() {
        // 通常のカラム（カラム名）
        @Override
        public void visit(Column column) {
System.out.println("column=" + column.getColumnName() + "\t" + column.getFullyQualifiedName());
        }

        // 定数（long）
        @Override
        public void visit(LongValue value) {
System.out.println("longValue=" + value.getValue());
        }

        // 関数
        @Override
        public void visit(Function function) {
System.out.println("Function\t" + function);

            String name = function.getName();
System.out.println("name=" + name);

            ExpressionList parameters = function.getParameters();
            System.out.println("parameters=" + parameters);
        }

        // AND
        @Override
        public void visit(AndExpression expr) {
            System.out.println("and");
            super.visit(expr);
        }

        // =（等値比較）
        @Override
        public void visit(EqualsTo expr) {
            System.out.println("=");
            super.visit(expr);
        }

        // ?
        @Override
        public void visit(JdbcParameter parameter) {
            System.out.println("JdbcParameter\t" + parameter.getIndex());
        }

        // :name
        @Override
        public void visit(JdbcNamedParameter parameter) {
            System.out.println("JdbcNamedParameter\t" + parameter.getName());
        }
    };

    /** */
    public boolean execute(String sql, String[] x) throws SQLException {
        return false;
    }

    /** */
    public boolean execute(String sql, int[] x) throws SQLException {
        return false;
    }

    /** */
    public java.sql.ResultSet getResultSet() throws SQLException {
        return currentResultSet;
    }

    /** TODO */
    public int getUpdateCount() throws SQLException {
        return 0;
    }

    /** */
    public boolean getMoreResults() throws SQLException {
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
    public int getResultSetConcurrency() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getResultSetType() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void addBatch(String sql) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public void clearBatch() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int[] executeBatch() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public java.sql.Connection getConnection() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public java.sql.ResultSet getGeneratedKeys() {
        return null;
    }

    /**
     * @see java.sql.Statement#getResultSetHoldability()
     */
    public int getResultSetHoldability() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.Statement#getMoreResults(int)
     */
    public boolean getMoreResults(int arg0) throws SQLException {
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
