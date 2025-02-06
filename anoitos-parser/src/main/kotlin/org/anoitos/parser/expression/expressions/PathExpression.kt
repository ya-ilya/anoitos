package org.anoitos.parser.expression.expressions

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.element.ParserElement
import org.anoitos.parser.element.TokenElement
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.expression.ExpressionParser

data class PathExpression(
    val elements: List<ParserElement>
) : Expression {
    companion object : ExpressionParser<PathExpression> {
        override fun parse(input: List<Token>): Pair<Int, PathExpression>? {
            val expressions = mutableListOf<ParserElement>()
            var size = 0

            while (size < input.size) {
                var found = false

                val callExpression = CallExpression.parse(input.drop(size))
                if (callExpression != null) {
                    expressions.add(callExpression.second)
                    size += callExpression.first
                    found = true
                }

                if (!found && input[size].type == TokenType.ID) {
                    expressions.add(TokenElement(input[size]))
                    size++
                    found = true
                }

                if (!found) {
                    return null
                }

                if (size < input.size && input[size].type == TokenType.DOT) {
                    size++
                } else {
                    break
                }
            }

            return if (!expressions.any { it is TokenElement }) {
                null
            } else {
                size to PathExpression(expressions)
            }
        }
    }
}