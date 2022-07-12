package vavi.sql.mdb.jdbc;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.List;

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
import vavi.apps.mdbtools.MdbFile;
import vavi.apps.mdbtools.Table;
import vavi.util.Debug;


/**
 * Engine.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-07-13 nsano initial version <br>
 */
class Engine {

    /** */
    JSqlParser parser = new CCJSqlParserManager();

    List<Object[]> valuesList;

    String table;
    boolean isSelect;
    boolean isSelectAll;

    MdbFile mdb;

    Engine(MdbFile mdb) {
        this.mdb = mdb;
    }

    boolean excute(String sql) throws SQLException {
        try {
            Reader reader = new StringReader(sql);
            net.sf.jsqlparser.statement.Statement statement = parser.parse(reader);
//Debug.println("statement: " + statement);

            statement.accept(statementVisitor);

            if (this.isSelect) {
                Table table = mdb.getTable(this.table);
                this.valuesList = table.fetchRows();
                return valuesList.size() != 0;
            } else {
                return false; // TODO
            }
        } catch (JSQLParserException | IOException e) {
            throw new SQLException(e);
        }
    }

    List<Object[]> getValues() {
        return valuesList;
    }

    private SelectVisitor selectVisitor = new SelectVisitorAdapter() {

        @Override
        public void visit(PlainSelect plainSelect) {
Debug.println("plainSelect\t" + plainSelect);

            FromItem from = plainSelect.getFromItem();
Debug.println("FromItem=" + from);
            table = from.toString();

            List<SelectItem> itemList = plainSelect.getSelectItems();
            for (SelectItem item : itemList) {
Debug.println("SelectItem=" + item);
                item.accept(selectItemVisitor);
            }

            Expression where = plainSelect.getWhere();
Debug.println("where=" + where);
            if (where != null) {
                where.accept(expressionVisitor);
            }
        }
    };

    StatementVisitorAdapter statementVisitor = new StatementVisitorAdapter() {
        @Override
        public void visit(Select select) {
Debug.println("select\t" + select);
            isSelect = true;
            SelectBody body = select.getSelectBody();
Debug.println("body\t" + body);
            body.accept(selectVisitor);
        }
    };

    private SelectItemVisitor selectItemVisitor = new SelectItemVisitorAdapter() {
        // *
        @Override
        public void visit(AllColumns columns) {
Debug.println("AllColumns\t" + columns);
            isSelectAll = true;
        }

        // t.*
        @Override
        public void visit(AllTableColumns columns) {
Debug.println("AllTableColumns\t" + columns);
            isSelectAll = true;
        }

        // 通常のカラム
        @Override
        public void visit(SelectExpressionItem item) {
Debug.println("SelectExpressionItem\t" + item);

            Alias alias = item.getAlias();
Debug.println("alias=" + alias);

            Expression expression = item.getExpression();
Debug.println("expression=" + expression);

            expression.accept(expressionVisitor);
        }
    };

    private ExpressionVisitor expressionVisitor = new ExpressionVisitorAdapter() {
        // 通常のカラム（カラム名）
        @Override
        public void visit(Column column) {
Debug.println("column=" + column.getColumnName() + "\t" + column.getFullyQualifiedName());
        }

        // 定数（long）
        @Override
        public void visit(LongValue value) {
Debug.println("longValue=" + value.getValue());
        }

        // 関数
        @Override
        public void visit(Function function) {
Debug.println("Function\t" + function);

            String name = function.getName();
Debug.println("name=" + name);

            ExpressionList parameters = function.getParameters();
Debug.println("parameters=" + parameters);
        }

        // AND
        @Override
        public void visit(AndExpression expr) {
Debug.println("and");
            super.visit(expr);
        }

        // =（等値比較）
        @Override
        public void visit(EqualsTo expr) {
Debug.println("=");
            super.visit(expr);
        }

        // ?
        @Override
        public void visit(JdbcParameter parameter) {
Debug.println("JdbcParameter\t" + parameter.getIndex());
        }

        // :name
        @Override
        public void visit(JdbcNamedParameter parameter) {
Debug.println("JdbcNamedParameter\t" + parameter.getName());
        }
    };
}
