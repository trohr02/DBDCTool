package org.tomasrohr.dbdc.core

import org.tomasrohr.dbdc.excp.TableNotFoundException

interface Protocoder {

    fun columnList(schemaObjectStr: String): String

    fun columnListCompact(schemaObjectStr: String): String

    fun selectFrom(schemaObjectStr: String): String

}
