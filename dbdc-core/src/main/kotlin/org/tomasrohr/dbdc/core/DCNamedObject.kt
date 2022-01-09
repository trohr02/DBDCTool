package org.tomasrohr.dbdc.core


interface DCNamedObject {


    /**
     * Returnms DC object's name
     */
    val name: String

    /**
     * Returns parent DC Object of this DC Object
     *
     * @return parent DC Object of this DC Object
     */
    fun getParent(): DCNamedObject?

    fun ql(): String

}
