package vavi.sql;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
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
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import vavi.util.Debug;


/**
 * Engine.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-07-13 nsano initial version <br>
 * @see "https://www.ne.jp/asahi/hishidama/home/tech/java/sql/parser/jsqlparser.html"
 */
public class Engine {

    /** */
    private JSqlParser parser = new CCJSqlParserManager();

    /** result set */
    private List<Object[]> results;

    /** TODO would be contained table name */
    private List<String> tableColumnNames = new ArrayList<>();

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
    private ResultSettable database;

    /** */
    private DatabaseMetaData metaData;

    /** */
    public Engine(ResultSettable database, DatabaseMetaData metaData) {
        this.database = database;
        this.metaData = metaData;
    }

    /** primitives of where clause */
    private interface Phrase {
        Function<Object[], Object> eval(Phrase... prims);
    }

    /** Polish Notation */
    private List<Phrase> whereClause = new ArrayList<>();

    /** queried column value */
    private class ColumnPhrase implements Phrase {
        /** database column no: 0~ */
        int index;
        /** @param name column name (TODO would be contained table name) */
        ColumnPhrase(String name) {
            index = tableColumnNames.indexOf(name);
            assert index >= 0 : index + ", " + name;
Debug.println("column: " + name + ", " + index);
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

    /** delayed operator execution */
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
//Debug.println("statement: " + statement);

            statement.accept(statementVisitor);

            if (this.isSelect) {
                database.setTable(table);
                results = database.getValues();
                return results.size() != 0;
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
//Debug.println("statement: " + statement);

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
Debug.println("where: " + first);

            if (this.isSelect) {
                database.setTable(this.table);
                this.results = database.getValues().stream().filter(cs -> (boolean) first.eval().apply(cs)).collect(Collectors.toList());
Debug.println("results: " + results.size());
                return results.size() != 0;
            } else {
Debug.println("not select");
                return false; // TODO
            }
        } catch (JSQLParserException e) {
            throw new IOException(e);
        }
    }

    /** */
    private SelectVisitor selectVisitor = new SelectVisitorAdapter() {

        @Override
        public void visit(PlainSelect plainSelect) {
Debug.println("[parser] plainSelect\t" + plainSelect);

            FromItem from = plainSelect.getFromItem();
Debug.println("[parser] FromItem=" + from);
            table = from.toString();

            List<SelectItem> itemList = plainSelect.getSelectItems();
            for (SelectItem item : itemList) {
Debug.println("[parser] SelectItem=" + item);
                item.accept(selectItemVisitor);
            }

            Expression where = plainSelect.getWhere();
Debug.println("[parser] where=" + where);

            whereClause.clear();

            if (where != null) {
                where.accept(expressionVisitor);
            }
        }
    };

    /** */
    private StatementVisitorAdapter statementVisitor = new StatementVisitorAdapter() {
        @Override
        public void visit(Select select) {
Debug.println("[parser] select\t" + select);
            isSelect = true;
            SelectBody body = select.getSelectBody();
Debug.println("[parser] body\t" + body);
            body.accept(selectVisitor);
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
Debug.println("columns[" + table + "]: " + tableColumnNames);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /** */
    private SelectItemVisitor selectItemVisitor = new SelectItemVisitorAdapter() {
        // *
        @Override
        public void visit(AllColumns columns) {
Debug.println("[parser] AllColumns\t" + columns);
            isSelectAll = true;
            fillTableColumnNames();
        }

        // t.*
        @Override
        public void visit(AllTableColumns columns) {
Debug.println("[parser] AllTableColumns\t" + columns);
        }

        // normal column
        @Override
        public void visit(SelectExpressionItem item) {
Debug.println("[parser] SelectExpressionItem\t" + item);

            Alias alias = item.getAlias();
Debug.println("[parser] alias=" + alias);

            Expression expression = item.getExpression();
Debug.println("[parser] expression=" + expression);

            expression.accept(expressionVisitor);
        }
    };

    /** */
    private ExpressionVisitor expressionVisitor = new ExpressionVisitorAdapter() {
        // normal column（column name）
        @Override
        public void visit(Column column) {
Debug.println("[parser] column=" + column.getColumnName() + "\t" + column.getFullyQualifiedName());
            whereClause.add(new ColumnPhrase(column.getColumnName()));
        }

        // constant（long）
        @Override
        public void visit(LongValue value) {
Debug.println("[parser] longValue=" + value.getValue());
        }

        // function
        @Override
        public void visit(net.sf.jsqlparser.expression.Function function) {
Debug.println("[parser] Function\t" + function);

            String name = function.getName();
Debug.println("[parser] name=" + name);

            ExpressionList parameters = function.getParameters();
Debug.println("[parser] parameters=" + parameters);
        }

        // "AND"
        @Override
        public void visit(AndExpression expr) {
Debug.println("[parser] and");
            super.visit(expr);
            whereClause.add(OpPhrase.LOGICAL_AND);
        }

        // "OR"
        @Override
        public void visit(OrExpression expr) {
            Debug.println("[parser] or");
            super.visit(expr);
            whereClause.add(OpPhrase.LOGICAL_OR);
        }

        // "="（equals）
        @Override
        public void visit(EqualsTo expr) {
Debug.println("[parser] =");
            super.visit(expr);
            whereClause.add(OpPhrase.EQUALS);
        }

        // jdbc "?"
        @Override
        public void visit(JdbcParameter parameter) {
Debug.println("[parser] JdbcParameter\t" + parameter.getIndex());
            whereClause.add(new ParamPhrase(parameter.getIndex()));
        }

        // jdbc ":name"
        @Override
        public void visit(JdbcNamedParameter parameter) {
Debug.println("[parser] JdbcNamedParameter\t" + parameter.getName());
            whereClause.add(new ParamPhrase(parameter.getName()));
        }
    };
}
