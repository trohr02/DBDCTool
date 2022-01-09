package org.tomasrohr.dbdc.core.bare

import com.sun.xml.internal.ws.server.sei.EndpointResponseMessageBuilder
import org.tomasrohr.dbdc.core.DCNamedObject


/**
 * Default database vendor neutral implementation of DCNamedObject
 */
open class BareNamedObject(
        name: String,
        parent: DCNamedObject?
) : DCNamedObject {

    /**
     * DC object's name. Should be 128 utf8 characters.
     */
    override val name: String = name
    /**
     * DC object's parent
     */
    private var parent: DCNamedObject? = parent
    override fun getParent(): DCNamedObject? {
        return parent
    }


    override fun ql(): String = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BareNamedObject

        if (name != other.name) return false
        if (parent != other.parent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + parent.hashCode()
        return result
    }

    override fun toString(): String {
        return "$parent->$name"
    }


}

val DCO_EMPTY: DCNamedObject = BareNamedObject("", null)
