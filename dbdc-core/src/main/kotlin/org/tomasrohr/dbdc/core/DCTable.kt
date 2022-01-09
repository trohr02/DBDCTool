package org.tomasrohr.dbdc.core

interface DCTable : DCNamedObject {


    val columns: Map<String, DCColumn>

    val columnNames: List<String>

    val isGT: Boolean


    /**
     * Get schema where DC object is located
     * @return schema where DC object is located
     */
    val schema: DCNamedObject

    val schemaName: String

    /**
     * Get database name (from data catalog) where the DC object is located.
     * @return database name (from data catalog) where the DC object is located. This can return null.
     */
    val db: DCNamedObject
    val dbName: String

    override fun toString(): String


}
