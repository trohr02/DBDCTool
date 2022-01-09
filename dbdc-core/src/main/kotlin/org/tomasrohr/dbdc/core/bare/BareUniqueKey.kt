package org.tomasrohr.dbdc.core.bare

import org.tomasrohr.dbdc.core.*

import java.util.Arrays

open class BareUniqueKey(
        name: String,
        columns: List<DCColumn>,
        parent: DCNamedObject?
) : BareNamedObject(name, parent), DCUniqueKey {

    override val columns: List<DCColumn> = columns
    override val columnNames: List<String> = columns.map{name}
}
