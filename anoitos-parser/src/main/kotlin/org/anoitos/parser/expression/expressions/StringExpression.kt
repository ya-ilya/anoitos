package org.anoitos.parser.expression.expressions

import org.anoitos.interpreter.context.Context
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.expression.ExpressionParser

data class StringExpression(val value: String) : Expression {
    companion object : ExpressionParser<StringExpression> {
        override fun parse(input: List<Token>): Pair<Int, StringExpression>? {
            if (input[0].type != TokenType.STRING) {
                return null
            }

            return 1 to StringExpression(input[0].value)
        }
    }

    override fun interpret(context: Context): Any {
        return value
    }
}