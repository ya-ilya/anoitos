package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

class ContinueStatement : Statement {
    companion object : StatementParser<ContinueStatement> {
        override fun parse(input: List<Token>): Pair<Int, ContinueStatement>? {
            val (size, _) = input.search(
                TokenType.CONTINUE,
                TokenType.SEMICOLON
            ) ?: return null

            return size to ContinueStatement()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "ContinueStatement()"
    }
}