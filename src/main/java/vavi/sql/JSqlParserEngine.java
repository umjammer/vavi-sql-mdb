/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.System.Logger.Level;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
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
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;


/**
 * JSqlParserEngine.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-07-13 nsano initial version <br>
 * @see "https://www.ne.jp/asahi/hishidama/home/tech/java/sql/parser/jsqlparser.html"
 */
public class JSqlParserEngine extends EngineBase {

    /** */
    private final JSqlParser parser = new CCJSqlParserManager();

    @Override
    protected void executeInternal(String sql) throws IOException {
        try {
            Reader reader = new StringReader(sql);
            net.sf.jsqlparser.statement.Statement statement = parser.parse(reader);
logger.log(Level.TRACE, "statement: " + statement);

            statement.accept(statementVisitor);
        } catch (JSQLParserException e) {
            throw new IOException(e);
        }
    }

    @Override
    protected void executeInternal(String sql, Map<Integer, Object> params) throws IOException {
        try {
            this.params = params;

            Reader reader = new StringReader(sql);
            net.sf.jsqlparser.statement.Statement statement = parser.parse(reader);
logger.log(Level.TRACE, "statement: " + statement);

            statement.accept(statementVisitor);
        } catch (JSQLParserException e) {
            throw new IOException(e);
        }
    }

    /** */
    private final SelectVisitor selectVisitor = new SelectVisitorAdapter() {

        @Override
        public void visit(PlainSelect plainSelect) {
logger.log(Level.DEBUG, "[parser] plainSelect\t" + plainSelect);

            FromItem from = plainSelect.getFromItem();
logger.log(Level.DEBUG, "[parser] FromItem=" + from);
            table = from.toString();

            List<SelectItem<?>> itemList = plainSelect.getSelectItems();
            for (SelectItem<?> item : itemList) {
logger.log(Level.DEBUG, "[parser] SelectItem=" + item);
                item.accept(selectItemVisitor);
            }

            Expression where = plainSelect.getWhere();
logger.log(Level.DEBUG, "[parser] where=" + where);

            whereClause.clear();

            if (where != null) {
                where.accept(expressionVisitor);
            }
        }
    };

    /** */
    private final StatementVisitorAdapter statementVisitor = new StatementVisitorAdapter() {

        @Override
        public void visit(Select select) {
logger.log(Level.DEBUG, "[parser] select\t" + select);
            isSelect = true;
logger.log(Level.DEBUG, "[parser] body\t" + select);
            select.accept(selectVisitor);
        }
    };

    /** */
    private final SelectItemVisitor selectItemVisitor = new SelectItemVisitorAdapter() {

        @Override
        public void visit(SelectItem item) {
logger.log(Level.DEBUG, "[parser] SelectExpressionItem\t" + item);

            Alias alias = item.getAlias();
logger.log(Level.DEBUG, "[parser] alias=" + alias);

            Expression expression = item.getExpression();
logger.log(Level.DEBUG, "[parser] expression=" + expression);

            if (expression instanceof AllTableColumns) {
                // t.*
logger.log(Level.DEBUG, "[parser] AllTableColumns\t" + expression);
            }

            if (expression instanceof AllColumns) {
                // *
logger.log(Level.DEBUG, "[parser] AllColumns\t" + expression);
                isSelectAll = true;
                fillTableColumnNames();
            }

            expression.accept(expressionVisitor);
        }
    };

    /** */
    private final ExpressionVisitor expressionVisitor = new ExpressionVisitorAdapter() {

        // normal column（column name）
        @Override
        public void visit(Column column) {
logger.log(Level.DEBUG, "[parser] column=" + column.getColumnName() + "\t" + column.getFullyQualifiedName());
            whereClause.add(new ColumnPhrase(column.getColumnName()));
        }

        // constant（long）
        @Override
        public void visit(LongValue value) {
logger.log(Level.DEBUG, "[parser] longValue=" + value.getValue());
        }

        // function
        @Override
        public void visit(net.sf.jsqlparser.expression.Function function) {
logger.log(Level.DEBUG, "[parser] Function\t" + function);

            String name = function.getName();
logger.log(Level.DEBUG, "[parser] name=" + name);

            ExpressionList<?> parameters = function.getParameters();
logger.log(Level.DEBUG, "[parser] parameters=" + parameters);
        }

        // "AND"
        @Override
        public void visit(AndExpression expr) {
logger.log(Level.DEBUG, "[parser] and");
            super.visit(expr);
            whereClause.add(OpPhrase.LOGICAL_AND);
        }

        // "OR"
        @Override
        public void visit(OrExpression expr) {
logger.log(Level.DEBUG, "[parser] or");
            super.visit(expr);
            whereClause.add(OpPhrase.LOGICAL_OR);
        }

        // "="（equals）
        @Override
        public void visit(EqualsTo expr) {
logger.log(Level.DEBUG, "[parser] =");
            super.visit(expr);
            whereClause.add(OpPhrase.EQUALS);
        }

        // jdbc "?"
        @Override
        public void visit(JdbcParameter parameter) {
logger.log(Level.DEBUG, "[parser] JdbcParameter\t" + parameter.getIndex());
            whereClause.add(new ParamPhrase(parameter.getIndex()));
        }

        // jdbc ":name"
        @Override
        public void visit(JdbcNamedParameter parameter) {
logger.log(Level.DEBUG, "[parser] JdbcNamedParameter\t" + parameter.getName());
            whereClause.add(new ParamPhrase(parameter.getName()));
        }
    };
}
