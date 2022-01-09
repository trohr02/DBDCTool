package org.tomasrohr.dbdc.core.teradata

import org.tomasrohr.dbdc.core.DCColumn
import org.tomasrohr.dbdc.core.DCNamedObject
import org.tomasrohr.dbdc.core.bare.BareColumn

open class TdColumn(
        name: String,
        dataType: String,
        length: Int,
        precision: Int,
        scale: Int,
        isNotNull: Boolean,
        defaultValue: String,
        val internalDataType: String,
        val isUnicode: Boolean
) : BareColumn(name, dataType, length, precision, scale, isNotNull, defaultValue), DCColumn
