/*
 * Copyright (c) 2004 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.sql.mdb.jdbc;

import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;


/**
 * DatabaseMetaData.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 040620 nsano initial version <br>
 */
public class DatabaseMetaData implements java.sql.DatabaseMetaData {

    String url;

    public DatabaseMetaData(String url) {
        this.url = url;
    }

    @Override
    public boolean allProceduresAreCallable() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean allTablesAreSelectable() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getURL() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getUserName() throws SQLException {
        return null;
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean nullsAreSortedHigh() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean nullsAreSortedLow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean nullsAreSortedAtStart() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean nullsAreSortedAtEnd() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getDatabaseProductName() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getDatabaseProductVersion() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getDriverName() throws SQLException {
        return Driver.class.getName();
    }

    @Override
    public String getDriverVersion() throws SQLException {
        return Driver.MAJOR_VERSION + "." + Driver.MINOR_VERSION;
    }

    @Override
    public int getDriverMajorVersion() {
        return Driver.MAJOR_VERSION;
    }

    @Override
    public int getDriverMinorVersion() {
        return Driver.MINOR_VERSION;
    }

    @Override
    public boolean usesLocalFiles() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean usesLocalFilePerTable() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsMixedCaseQuotedIdentifiers()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getIdentifierQuoteString() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getSQLKeywords() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getNumericFunctions() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getStringFunctions() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getSystemFunctions() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getTimeDateFunctions() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getSearchStringEscape() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getExtraNameCharacters() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsColumnAliasing() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean nullPlusNonNullIsNull() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsConvert() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsConvert(int fromType, int toType)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsTableCorrelationNames() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsDifferentTableCorrelationNames()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsOrderByUnrelated() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsGroupBy() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsGroupByUnrelated() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsLikeEscapeClause() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsMultipleResultSets() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsMultipleTransactions() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsNonNullableColumns() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsCoreSQLGrammar() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsANSI92FullSQL() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsIntegrityEnhancementFacility()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsOuterJoins() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsFullOuterJoins() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsLimitedOuterJoins() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getSchemaTerm() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getProcedureTerm() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getCatalogTerm() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean isCatalogAtStart() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String getCatalogSeparator() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsSchemasInDataManipulation()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsSchemasInTableDefinitions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsSchemasInIndexDefinitions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsSchemasInPrivilegeDefinitions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsCatalogsInDataManipulation()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsCatalogsInTableDefinitions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsCatalogsInIndexDefinitions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsCatalogsInPrivilegeDefinitions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsPositionedDelete() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsPositionedUpdate() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsSelectForUpdate() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsStoredProcedures() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsSubqueriesInExists() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsSubqueriesInIns() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsUnion() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsUnionAll() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsOpenCursorsAcrossRollback()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsOpenStatementsAcrossCommit()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsOpenStatementsAcrossRollback()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxBinaryLiteralLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxCharLiteralLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxColumnNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxColumnsInGroupBy() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxColumnsInIndex() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxColumnsInOrderBy() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxColumnsInSelect() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxColumnsInTable() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxConnections() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxCursorNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxIndexLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxSchemaNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxProcedureNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxCatalogNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxRowSize() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxStatementLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxStatements() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxTableNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxTablesInSelect() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getMaxUserNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getDefaultTransactionIsolation() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsTransactions() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsTransactionIsolationLevel(int level)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsDataDefinitionAndDataManipulationTransactions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsDataManipulationTransactionsOnly()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean dataDefinitionCausesTransactionCommit()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean dataDefinitionIgnoredInTransactions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getProcedures(String catalog, String schemaPattern,
                                   String procedureNamePattern)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getProcedureColumns(String catalog, String schemaPattern,
                                         String procedureNamePattern,
                                         String columnNamePattern)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getTables(String catalog, String schemaPattern,
                               String tableNamePattern, String[] types)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getSchemas() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getCatalogs() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getTableTypes() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getColumns(String catalog, String schemaPattern,
                                String tableNamePattern,
                                String columnNamePattern)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getColumnPrivileges(String catalog, String schema,
                                         String table, String columnNamePattern)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getTablePrivileges(String catalog, String schemaPattern,
                                        String tableNamePattern)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getBestRowIdentifier(String catalog, String schema,
                                          String table, int scope,
                                          boolean nullable)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getVersionColumns(String catalog, String schema,
                                       String table) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getPrimaryKeys(String catalog, String schema, String table)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getImportedKeys(String catalog, String schema, String table)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getExportedKeys(String catalog, String schema, String table)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getCrossReference(String primaryCatalog,
                                       String primarySchema,
                                       String primaryTable,
                                       String foreignCatalog,
                                       String foreignSchema, String foreignTable)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getTypeInfo() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ResultSet getIndexInfo(String catalog, String schema, String table,
                                  boolean unique, boolean approximate)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsResultSetType(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsResultSetConcurrency(int type, int concurrency)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean ownDeletesAreVisible(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean ownInsertsAreVisible(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean othersDeletesAreVisible(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean othersInsertsAreVisible(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean updatesAreDetected(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean deletesAreDetected(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean insertsAreDetected(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean supportsBatchUpdates() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public java.sql.ResultSet getUDTs(String catalog,
                                      String schemaPattern,
                                      String typeNamePattern,
                                      int[] types)
        throws SQLException {

        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public java.sql.Connection getConnection() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public java.sql.ResultSet getAttributes(String catalog,
                                            String schemaPattern,
                                            String typeNamePattern,
                                            String attributeNamePattern) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int getDatabaseMajorVersion() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getDatabaseMinorVersion() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getJDBCMajorVersion() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getJDBCMinorVersion() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getSQLStateType() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean locatorsUpdateCopy() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean supportsGetGeneratedKeys() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean supportsMultipleOpenResults() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean supportsNamedParameters() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean supportsSavepoints() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean supportsStatementPooling() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean supportsResultSetHoldability(int arg0) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ResultSet getSuperTables(String arg0, String arg1, String arg2) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultSet getSuperTypes(String arg0, String arg1, String arg2) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ResultSet getClientInfoProperties() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        // TODO Auto-generated method stub
        return false;
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
    public ResultSet getPseudoColumns(String catalog,
                                      String schemaPattern,
                                      String tableNamePattern,
                                      String columnNamePattern) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
}

/* */
