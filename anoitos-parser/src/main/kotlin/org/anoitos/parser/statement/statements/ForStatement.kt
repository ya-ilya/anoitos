package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.ParserResult
import org.anoitos.parser.expression.expressions.NumberExpression
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class ForStatement(
    val identifier: Token,
    val from: NumberExpression,
    val to: NumberExpression,
    val body: BlockStatement
) : Statement {
    companion object : StatementParser<ForStatement> {
        override fun parse(input: List<Token>): ParserResult<ForStatement>? {
            val (size, _, _, identifier, _, from, _, to, _, _, body, _) = input.search(
                TokenType.FOR,
                TokenType.LPAREN,
                TokenType.ID,
                TokenType.IN,
                TokenType.NUMBER,
                TokenType.RANGE,
                TokenType.NUMBER,
                TokenType.RPAREN,
                TokenType.LBRACE,
                TokenType.SEARCH_GROUP,
                TokenType.RBRACE
            ) ?: return null

            return ParserResult(
                size,
                ForStatement(
                    identifier[0],
                    NumberExpression.parse(from)!!.element,
                    NumberExpression.parse(to)!!.element,
                    BlockStatement.parse(body).element
                )
            )
        }
    }
}