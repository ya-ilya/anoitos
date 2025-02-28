package org.anoitos.parser.expression.expressions

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.ParserResult
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.expression.ExpressionParser

data class StringExpression(val value: String) : Expression {
    companion object : ExpressionParser<StringExpression> {
        override fun parse(input: List<Token>): ParserResult<StringExpression>? {
            if (input[0].type != TokenType.STRING) {
                return null
            }

            return ParserResult(
                1,
                StringExpression(input[0].value)
            )
        }
    }
}