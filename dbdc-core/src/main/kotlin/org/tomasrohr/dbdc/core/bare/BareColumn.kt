package org.tomasrohr.dbdc.core.bare

import org.tomasrohr.dbdc.core.DCColumn
import org.tomasrohr.dbdc.core.DCNamedObject

open class BareColumn(
        name: String,
        dataType: String,
        length: Int,
        precision: Int,
        scale: Int,
        isNotNull: Boolean,
        defaultValue: String
) : BareNamedObject(name, null), DCColumn {


    override val dataType: String = dataType
    override val length: Int = length

    override val precision: Int = precision
    override val scale: Int = scale
    override val isNotNull: Boolean = isNotNull
    override val defaultValue: String = defaultValue


    override fun toString(): String {
        return "$name $dataType"
    }

    override fun ql(): String {
        return name
    }
}

