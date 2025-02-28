package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.ParserResult
import org.anoitos.parser.element.ParserElement
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class ReturnStatement(
    val value: ParserElement
) : Statement {
    companion object : StatementParser<ReturnStatement> {
        override fun parse(input: List<Token>): ParserResult<ReturnStatement>? {
            val (size, _, value, _) = input.search(
                TokenType.RETURN,
                TokenType.SEARCH_GROUP,
                TokenType.SEMICOLON
            ) ?: return null

            return ParserResult(
                size,
                ReturnStatement(
                    Parser.parseElement(value)!!.element
                )
            )
        }
    }
}