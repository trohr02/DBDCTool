package org.tomasrohr.dbdc.core.bare

import org.tomasrohr.dbdc.core.*

import java.util.ArrayList
import java.util.HashMap

open class BareTable(
        name: String,
        schema: DCNamedObject,
        db: DCNamedObject,
        parent: DCNamedObject?,
        columns: Map<String, DCColumn>,
        isGT: Boolean
) : BareNamedObject(name, parent), DCTable {

    private val NL = System.lineSeparator()

    override val isGT: Boolean = false;
    override val columns: Map<String, DCColumn> = columns
    override val columnNames: List<String> = columns.keys.toList()


    override val schema: DCNamedObject = schema
    override val schemaName: String = schema.name

    override val db: DCNamedObject = db
    override val dbName: String = db.name


    /*
    @Override
    public void setColumns(List<DCColumn> columns) {
        for (DCColumn column : columns) {
            this.columns.put(column.getName(), column);
        }
    }
    */

    override fun toString(): String {
        val sb = StringBuilder(1000)
        sb.append("Table: ")
                .append(schema)
                .append(".")
                .append(name)
                .append(NL)
        for (column in columns.values) {
            sb.append(column.name)
                    .append("  ")
                    .append(column.dataType)
                    .append(NL)
        }
        return sb.toString()
    }


}
