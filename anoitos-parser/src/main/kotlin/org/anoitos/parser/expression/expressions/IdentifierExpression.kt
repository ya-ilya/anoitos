package org.anoitos.parser.expression.expressions

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.expression.ExpressionParser

data class IdentifierExpression(
    val identifier: String
) : Expression {
    companion object : ExpressionParser<IdentifierExpression> {
        override fun parse(input: List<Token>): Pair<Int, IdentifierExpression>? {
            if (input[0].type != TokenType.ID) {
                return null
            }

            return 1 to IdentifierExpression(input[0].value)
        }
    }
}