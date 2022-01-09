package org.tomasrohr.dbdc.core

interface DCIndex : DCNamedObject {

    val columns: List<DCColumn>
    val isUnique: Boolean

}
