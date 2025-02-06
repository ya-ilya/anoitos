package org.anoitos.parser.element

import org.anoitos.lexer.token.Token

class TokenElement(val token: Token) : ParserElement {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TokenElement

        return token == other.token
    }

    override fun hashCode(): Int {
        return token.hashCode()
    }

    override fun toString(): String {
        return "TokenElement(token=$token)"
    }
}