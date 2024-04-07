package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.expression.expressions.IdentifierExpression
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class SetStatement(
    val name: IdentifierExpression,
    val value: Statement
) : Statement {
    companion object : StatementParser<SetStatement> {
        override fun parse(input: List<Token>): Pair<Int, SetStatement>? {
            val (size, name, _, value, _) = input.search(
                TokenType.ID,
                TokenType.EQUAL,
                TokenType.SEARCH_GROUP,
                TokenType.SEMICOLON
            ) ?: return null

            return size to SetStatement(
                IdentifierExpression.parse(name)!!.second,
                Parser.parseStatement(value).second
            )
        }
    }
}