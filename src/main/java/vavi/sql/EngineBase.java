/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql;

import java.io.IOException;
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

import static java.lang.System.getLogger;


/**
 * EngineBase.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-05-30 nsano initial version <br>
 */
public abstract class EngineBase implements Engine {

    protected static final Logger logger = getLogger(JSqlParserEngine.class.getName());

    /** TODO would be contained table name */
    private final List<String> tableColumnNames = new ArrayList<>();

    /** result set */
    private List<Object[]> results;

    @Override
    public ResultSettable result() {
        return new ResultSettable() {
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

    protected String table;
    protected boolean isSelect;
    protected boolean isSelectAll;

    /** values set by a prepared statement */
    protected Map<Integer, Object> params;

    /** table name and columns name, type and value */
    private ResultSettable database;

    /** database metadata */
    private DatabaseMetaData metaData;

    @Override
    public void setDatabase(ResultSettable database) {
        this.database = database;
    }

    @Override
    public void setDatabaseMetaData(DatabaseMetaData metaData) {
        this.metaData = metaData;
    }

    /** primitives of where clause */
    protected interface Phrase {

        Function<Object[], Object> eval(Phrase... prims);
    }

    /** polish notation */
    protected final List<Phrase> whereClause = new ArrayList<>();

    /** operator logics */
    protected enum OpPhrase implements Phrase {
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

    /** lazy evaluation */
    protected static class FunctionPhrase implements Phrase {

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

    /** queried column value */
    protected class ColumnPhrase implements Phrase {

        /** database column no: 0 origin */
        int index;

        /** @param name column name TODO would be contained table name */
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
    protected class ParamPhrase implements Phrase {

        /** parameter index: 1 origin */
        int index;

        /** @param name column name */
        ParamPhrase(String name) {
            index = (int) IntStream.range(0, whereClause.size()).filter(i -> whereClause.get(i) instanceof JSqlParserEngine.ParamPhrase).count() + 1;
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

    /** engine specific routine */
    protected abstract void executeInternal(String sql) throws IOException;

    @Override
    public boolean execute(String sql) throws IOException {
        executeInternal(sql);

        if (this.isSelect) {
            database.setTable(table);
            results = database.getValues();
            return !results.isEmpty();
        } else {
            return false; // TODO
        }
    }

    /** engine specific routine */
    protected abstract void executeInternal(String sql, Map<Integer, Object> params) throws IOException;

    @Override
    public boolean execute(String sql, Map<Integer, Object> params) throws IOException {
        this.params = params;

        executeInternal(sql, params);

        // rpn
        Deque<EngineBase.Phrase> stack = new ArrayDeque<>();
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
    }

    /** metadata */
    protected void fillTableColumnNames() {
        try {
            java.sql.ResultSet rs = metaData.getColumns(null, null, table, null);
            tableColumnNames.clear();
            while (rs.next()) {
                tableColumnNames.add(rs.getString("COLUMN_NAME"));
            }
logger.log(Level.DEBUG, "columns[" + table + "]: " + tableColumnNames);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
