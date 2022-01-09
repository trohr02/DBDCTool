package org.tomasrohr.dbdc.core

import java.sql.SQLException
import java.util.HashMap

interface DataCatalog {

    val allSchemas: List<String>
    val allDbs: List<String>

    /**
     * Get HashMap of all tables in the data catalog visible to user.
     * @return a HashMap of all tables in data catalog visible to user
     */
    val allTables: Map<SchemaObjectName, DCTable>

    /**
     * Look up DCTable instance by schema and table name.
     * @param schema name
     * @param table name
     * @return a DCTable instance with details about partucular table
     */
    fun getTable(schema: String, name: String): DCTable

    /**
     * Look up DCTable instance by schema and table name.
     * @param o schema and table name
     * @return a DCTable instance with details about partucular table
     */
    fun getTable(schemaObjectName: SchemaObjectName): DCTable

    /**
     * Look up DCTable instance by schema and table name.
     * @param schemaObjectString schema and table name
     * @return a DCTable instance with details about partucular table
     */
    fun getTable(schemaObjectString: String): DCTable

    /**
     * Query data catalog and load all tables and their details.
     * This method actually runs a SQL query in the database.
     * @throws SQLException
     */
    @Throws(SQLException::class)
    fun loadTables()
}
