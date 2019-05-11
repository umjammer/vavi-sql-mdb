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

    /** */
    public boolean allProceduresAreCallable() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean allTablesAreSelectable() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getURL() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /* */
    public String getUserName() throws SQLException {
        return null;
    }

    /** */
    public boolean isReadOnly() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean nullsAreSortedHigh() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean nullsAreSortedLow() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean nullsAreSortedAtStart() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean nullsAreSortedAtEnd() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getDatabaseProductName() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getDatabaseProductVersion() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getDriverName() throws SQLException {
        return Driver.class.getName();
    }

    /** */
    public String getDriverVersion() throws SQLException {
        return Driver.MAJOR_VERSION + "." + Driver.MINOR_VERSION;
    }

    /** */
    public int getDriverMajorVersion() {
        return Driver.MAJOR_VERSION;
    }

    /** */
    public int getDriverMinorVersion() {
        return Driver.MINOR_VERSION;
    }

    /** */
    public boolean usesLocalFiles() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean usesLocalFilePerTable() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsMixedCaseQuotedIdentifiers()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getIdentifierQuoteString() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getSQLKeywords() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getNumericFunctions() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getStringFunctions() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getSystemFunctions() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getTimeDateFunctions() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getSearchStringEscape() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getExtraNameCharacters() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsColumnAliasing() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean nullPlusNonNullIsNull() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsConvert() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsConvert(int fromType, int toType)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsTableCorrelationNames() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsDifferentTableCorrelationNames()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsOrderByUnrelated() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsGroupBy() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsGroupByUnrelated() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsLikeEscapeClause() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsMultipleResultSets() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsMultipleTransactions() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsNonNullableColumns() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsCoreSQLGrammar() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsANSI92FullSQL() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsIntegrityEnhancementFacility()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsOuterJoins() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsFullOuterJoins() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsLimitedOuterJoins() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getSchemaTerm() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getProcedureTerm() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getCatalogTerm() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean isCatalogAtStart() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public String getCatalogSeparator() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsSchemasInDataManipulation()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsSchemasInTableDefinitions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsSchemasInIndexDefinitions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsSchemasInPrivilegeDefinitions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsCatalogsInDataManipulation()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsCatalogsInTableDefinitions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsCatalogsInIndexDefinitions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsCatalogsInPrivilegeDefinitions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsPositionedDelete() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsPositionedUpdate() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsSelectForUpdate() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsStoredProcedures() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsSubqueriesInExists() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsSubqueriesInIns() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsUnion() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsUnionAll() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsOpenCursorsAcrossRollback()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsOpenStatementsAcrossCommit()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsOpenStatementsAcrossRollback()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxBinaryLiteralLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxCharLiteralLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxColumnNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxColumnsInGroupBy() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxColumnsInIndex() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxColumnsInOrderBy() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxColumnsInSelect() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxColumnsInTable() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxConnections() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxCursorNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxIndexLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxSchemaNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxProcedureNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxCatalogNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxRowSize() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxStatementLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxStatements() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxTableNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxTablesInSelect() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getMaxUserNameLength() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public int getDefaultTransactionIsolation() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsTransactions() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsTransactionIsolationLevel(int level)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsDataDefinitionAndDataManipulationTransactions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsDataManipulationTransactionsOnly()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean dataDefinitionCausesTransactionCommit()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean dataDefinitionIgnoredInTransactions()
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getProcedures(String catalog, String schemaPattern,
                                   String procedureNamePattern)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getProcedureColumns(String catalog, String schemaPattern,
                                         String procedureNamePattern,
                                         String columnNamePattern)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getTables(String catalog, String schemaPattern,
                               String tableNamePattern, String[] types)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getSchemas() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getCatalogs() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getTableTypes() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getColumns(String catalog, String schemaPattern,
                                String tableNamePattern,
                                String columnNamePattern)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getColumnPrivileges(String catalog, String schema,
                                         String table, String columnNamePattern)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getTablePrivileges(String catalog, String schemaPattern,
                                        String tableNamePattern)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getBestRowIdentifier(String catalog, String schema,
                                          String table, int scope,
                                          boolean nullable)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getVersionColumns(String catalog, String schema,
                                       String table) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getPrimaryKeys(String catalog, String schema, String table)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getImportedKeys(String catalog, String schema, String table)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getExportedKeys(String catalog, String schema, String table)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getCrossReference(String primaryCatalog,
                                       String primarySchema,
                                       String primaryTable,
                                       String foreignCatalog,
                                       String foreignSchema, String foreignTable)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getTypeInfo() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public ResultSet getIndexInfo(String catalog, String schema, String table,
                                  boolean unique, boolean approximate)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsResultSetType(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsResultSetConcurrency(int type, int concurrency)
        throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean ownDeletesAreVisible(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean ownInsertsAreVisible(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean othersDeletesAreVisible(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean othersInsertsAreVisible(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean updatesAreDetected(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean deletesAreDetected(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean insertsAreDetected(int type) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public boolean supportsBatchUpdates() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public java.sql.ResultSet getUDTs(String catalog,
                                      String schemaPattern,
                                      String typeNamePattern,
                                      int[] types)
        throws SQLException {

        throw new UnsupportedOperationException("Not implemented.");
    }

    /** */
    public java.sql.Connection getConnection() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @throws SQLException */
    public java.sql.ResultSet getAttributes(String catalog,
                                            String schemaPattern,
                                            String typeNamePattern,
                                            String attributeNamePattern) throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * @see java.sql.DatabaseMetaData#getDatabaseMajorVersion()
     */
    public int getDatabaseMajorVersion() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.DatabaseMetaData#getDatabaseMinorVersion()
     */
    public int getDatabaseMinorVersion() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.DatabaseMetaData#getJDBCMajorVersion()
     */
    public int getJDBCMajorVersion() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.DatabaseMetaData#getJDBCMinorVersion()
     */
    public int getJDBCMinorVersion() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.DatabaseMetaData#getResultSetHoldability()
     */
    public int getResultSetHoldability() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.DatabaseMetaData#getSQLStateType()
     */
    public int getSQLStateType() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see java.sql.DatabaseMetaData#locatorsUpdateCopy()
     */
    public boolean locatorsUpdateCopy() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.DatabaseMetaData#supportsGetGeneratedKeys()
     */
    public boolean supportsGetGeneratedKeys() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.DatabaseMetaData#supportsMultipleOpenResults()
     */
    public boolean supportsMultipleOpenResults() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.DatabaseMetaData#supportsNamedParameters()
     */
    public boolean supportsNamedParameters() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.DatabaseMetaData#supportsSavepoints()
     */
    public boolean supportsSavepoints() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.DatabaseMetaData#supportsStatementPooling()
     */
    public boolean supportsStatementPooling() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.DatabaseMetaData#supportsResultSetHoldability(int)
     */
    public boolean supportsResultSetHoldability(int arg0) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see java.sql.DatabaseMetaData#getSuperTables(java.lang.String, java.lang.String, java.lang.String)
     */
    public ResultSet getSuperTables(String arg0, String arg1, String arg2) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.sql.DatabaseMetaData#getSuperTypes(java.lang.String, java.lang.String, java.lang.String)
     */
    public ResultSet getSuperTypes(String arg0, String arg1, String arg2) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.DatabaseMetaData#autoCommitFailureClosesAllResultSets() */
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /* @see java.sql.DatabaseMetaData#getClientInfoProperties() */
    public ResultSet getClientInfoProperties() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.DatabaseMetaData#getFunctionColumns(java.lang.String, java.lang.String, java.lang.String, java.lang.String) */
    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.DatabaseMetaData#getFunctions(java.lang.String, java.lang.String, java.lang.String) */
    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.DatabaseMetaData#getRowIdLifetime() */
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.DatabaseMetaData#getSchemas(java.lang.String, java.lang.String) */
    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.DatabaseMetaData#supportsStoredFunctionsUsingCallSyntax() */
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        // TODO Auto-generated method stub
        return false;
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

    /* @see java.sql.DatabaseMetaData#getPseudoColumns(java.lang.String, java.lang.String, java.lang.String, java.lang.String) */
    @Override
    public ResultSet getPseudoColumns(String catalog,
                                      String schemaPattern,
                                      String tableNamePattern,
                                      String columnNamePattern) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* @see java.sql.DatabaseMetaData#generatedKeyAlwaysReturned() */
    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
}

/* */
