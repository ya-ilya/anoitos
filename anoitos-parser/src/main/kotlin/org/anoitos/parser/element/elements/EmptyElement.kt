package org.anoitos.parser.element.elements

import org.anoitos.parser.element.ParserElement

object EmptyElement : ParserElement {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "EmptyElement()"
    }
}