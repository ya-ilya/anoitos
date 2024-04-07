package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.expression.expressions.IdentifierExpression
import org.anoitos.parser.expression.expressions.NumberExpression
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class ForStatement(
    val identifier: IdentifierExpression,
    val from: NumberExpression,
    val to: NumberExpression,
    val body: BlockStatement
) : Statement {
    companion object : StatementParser<ForStatement> {
        override fun parse(input: List<Token>): Pair<Int, ForStatement>? {
            val (size, _, _, identifier, _, from, _, to, _, _, body, _) = input.search(
                TokenType.FOR,
                TokenType.LPAREN,
                TokenType.ID,
                TokenType.IN,
                TokenType.INT,
                TokenType.RANGE,
                TokenType.INT,
                TokenType.RPAREN,
                TokenType.LBRACE,
                TokenType.SEARCH_GROUP,
                TokenType.RBRACE
            ) ?: return null

            return size to ForStatement(
                IdentifierExpression.parse(identifier)!!.second,
                NumberExpression.parse(from)!!.second,
                NumberExpression.parse(to)!!.second,
                BlockStatement.parse(body).second
            )
        }
    }
}