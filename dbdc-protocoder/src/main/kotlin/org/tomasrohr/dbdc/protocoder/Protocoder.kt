package org.tomasrohr.dbdc.core

import org.tomasrohr.dbdc.excp.TableNotFoundException

interface Protocoder {
    @Throws(TableNotFoundException::class)
    fun columnList(schemaObjectStr: String): String

    @Throws(TableNotFoundException::class)
    fun columnListCompact(schemaObjectStr: String): String

    @Throws(TableNotFoundException::class)
    fun selectFrom(schemaObjectStr: String): String
}