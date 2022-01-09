package org.tomasrohr.dbdc.core.teradata

import org.tomasrohr.dbdc.core.*
import org.tomasrohr.dbdc.core.bare.BareTable
import org.tomasrohr.dbdc.core.bare.DCO_EMPTY


class TdTable(
        name: String,
        db: DCNamedObject,
        parent: DCNamedObject,
        columns: Map<String, DCColumn>,
        isGT: Boolean
) : BareTable(name, DCO_EMPTY, db, db, columns, isGT), DCTable {


}
