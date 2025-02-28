package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.ParserResult
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

class BreakStatement : Statement {
    companion object : StatementParser<BreakStatement> {
        override fun parse(input: List<Token>): ParserResult<BreakStatement>? {
            val (size, _) = input.search(
                TokenType.BREAK,
                TokenType.SEMICOLON
            ) ?: return null

            return ParserResult(
                size,
                BreakStatement()
            )
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
        return "BreakStatement()"
    }
}