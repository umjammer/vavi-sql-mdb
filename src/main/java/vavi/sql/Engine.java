/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

import static java.lang.System.getLogger;


/**
 * Engine.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-07-13 nsano initial version <br>
 * @see "https://www.ne.jp/asahi/hishidama/home/tech/java/sql/parser/jsqlparser.html"
 */
public class Engine {

    private static final Logger logger = getLogger(Engine.class.getName());

    /** */
    private final JSqlParser parser = new CCJSqlParserManager();

    /** result set */
    private List<Object[]> results;

    /** TODO would be contained table name */
    private final List<String> tableColumnNames = new ArrayList<>();

    public ResultSettable result() {
        return new ResultSettable() {
            /** result set */
            @Override
            public List<Object[]> getValues() {
                return results;
            }

            @Override
            public String columnNameAt(int index) {
                return tableColumnNames.get(index);
            }
        };
    }

    private String table;
    private boolean isSelect;
    private boolean isSelectAll;

    /** values set by a prepared statement */
    private Map<Integer, Object> params;

    /** abstract database */
    private final ResultSettable database;

    /** */
    private final DatabaseMetaData metaData;

    /** */
    public Engine(ResultSettable database, DatabaseMetaData metaData) {
        this.database = database;
        this.metaData = metaData;
    }

    /** primitives of where clause */
    private interface Phrase {
        Function<Object[], Object> eval(Phrase... prims);
    }

    /** polish notation */
    private final List<Phrase> whereClause = new ArrayList<>();

    /** queried column value */
    private class ColumnPhrase implements Phrase {
        /** database column no: 0~ */
        int index;
        /** @param name column name (TODO would be contained table name) */
        ColumnPhrase(String name) {
            index = tableColumnNames.indexOf(name);
            assert index >= 0 : index + ", " + name;
logger.log(Level.DEBUG, "column: " + name + ", " + index);
        }
        /** @param prims ignored */
        public Function<Object[], Object> eval(Phrase... prims) {
            return cs -> cs[index];
        }
    }

    /** values set by a prepared statement */
    private class ParamPhrase implements Phrase {
        /** parameter index: 1~ */
        int index;
        /** @param name column name */
        ParamPhrase(String name) {
            index = (int) IntStream.range(0, whereClause.size()).filter(i -> whereClause.get(i) instanceof ParamPhrase).count() + 1;
            assert index > 0 : index + ", " + name;
        }
        ParamPhrase(int index) {
            this.index = index;
        }
        /** @param prims ignored */
        public Function<Object[], Object> eval(Phrase... prims) {
            return cs -> params.get(index);
        }
    }

    /** lazy evaluation */
    private static class FunctionPhrase implements Phrase {
        OpPhrase op;
        Phrase[] prims;
        public FunctionPhrase(OpPhrase op, Phrase... prims) {
            this.op = op;
            this.prims = prims;
        }
        /** @param prims ignored */
        @Override
        public Function<Object[], Object> eval(Phrase... prims) {
            return op.eval(this.prims);
        }
    }

    /** operator logics */
    private enum OpPhrase implements Phrase {
        EQUALS {
            @Override
            Phrase create(Deque<Phrase> stack) {
                return new FunctionPhrase(this, stack.pop(), stack.pop());
            }
            /** return boolean */
            public Function<Object[], Object> eval(Phrase[] prims) {
                return cs -> prims[0].eval().apply(cs).equals(prims[1].eval().apply(cs));
            }
        },
        LOGICAL_AND {
            @Override
            Phrase create(Deque<Phrase> stack) {
                return new FunctionPhrase(this, stack.pop(), stack.pop());
            }
            /** return boolean */
            public Function<Object[], Object> eval(Phrase[] prims) {
                return cs -> ((boolean) prims[0].eval().apply(cs)) && (((boolean) prims[1].eval().apply(cs)));
            }
        },
        LOGICAL_OR {
            @Override
            Phrase create(Deque<Phrase> stack) {
                return new FunctionPhrase(this, stack.pop(), stack.pop());
            }
            /** return boolean */
            public Function<Object[], Object> eval(Phrase[] prims) {
                return cs -> ((boolean) prims[0].eval().apply(cs)) || (((boolean) prims[1].eval().apply(cs)));
            }
        };
        abstract Phrase create(Deque<Phrase> stack);
    }

    /** */
    public boolean execute(String sql) throws IOException {
        try {
            Reader reader = new StringReader(sql);
            net.sf.jsqlparser.statement.Statement statement = parser.parse(reader);
logger.log(Level.TRACE, "statement: " + statement);

            statement.accept(statementVisitor);

            if (this.isSelect) {
                database.setTable(table);
                results = database.getValues();
                return !results.isEmpty();
            } else {
                return false; // TODO
            }
        } catch (JSQLParserException e) {
            throw new IOException(e);
        }
    }

    /**
     * @param params set by a prepared statement
     */
    public boolean execute(String sql, Map<Integer, Object> params) throws IOException {
        try {
            this.params = params;

            Reader reader = new StringReader(sql);
            net.sf.jsqlparser.statement.Statement statement = parser.parse(reader);
logger.log(Level.TRACE, "statement: " + statement);

            statement.accept(statementVisitor);

            // rpn
            Deque<Phrase> stack = new ArrayDeque<>();
            for (Phrase phrase : whereClause) {
                if (phrase instanceof OpPhrase) {
                    stack.push(((OpPhrase) phrase).create(stack));
                } else {
                    stack.push(phrase);
                }
            }
            Phrase first = stack.pop();
logger.log(Level.DEBUG, "where: " + first);

            if (this.isSelect) {
                database.setTable(this.table);
                this.results = database.getValues().stream().filter(cs -> (boolean) first.eval().apply(cs)).collect(Collectors.toList());
logger.log(Level.DEBUG, "results: " + results.size());
                return !results.isEmpty();
            } else {
logger.log(Level.DEBUG, "not select");
                return false; // TODO
            }
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
    void fillTableColumnNames() {
        try {
            java.sql.ResultSet rs = metaData.getColumns(null, null , table, null);
            tableColumnNames.clear();
            while (rs.next()) {
                tableColumnNames.add(rs.getString("COLUMN_NAME"));
            }
logger.log(Level.DEBUG, "columns[" + table + "]: " + tableColumnNames);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

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
