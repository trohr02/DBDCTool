package org.tomasrohr.dbdc.core


open class SchemaObjectName(val schema: String, val name: String) {

    override fun toString(): String {
        return "$schema.$name"
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as SchemaObjectName?

        return if (schema != that!!.schema) false else name == that.name
    }

    override fun hashCode(): Int {
        var result = schema.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    companion object {

        fun of(schema: String, table: String): SchemaObjectName {
            return SchemaObjectName(schema, table)
        }

        fun of(tableInSchema: String): SchemaObjectName {
            val args = tableInSchema.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return SchemaObjectName(args[0], args[1])
        }
    }

}
