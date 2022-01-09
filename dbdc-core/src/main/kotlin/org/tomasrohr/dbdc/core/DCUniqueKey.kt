package org.tomasrohr.dbdc.core

interface DCUniqueKey : DCNamedObject {

    val columnNames: List<String>
    val columns: List<DCColumn>

}
