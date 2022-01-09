package org.tomasrohr.dbdc.core

interface DCColumn : DCNamedObject {

    val dataType: String

    val length: Int

    val precision: Int

    val scale: Int

    val isNotNull: Boolean

    val defaultValue: String
}
