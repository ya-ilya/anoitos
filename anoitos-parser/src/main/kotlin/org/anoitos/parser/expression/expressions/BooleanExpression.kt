package org.anoitos.parser.expression.expressions

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.expression.ExpressionParser
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.statements.CallStatement
import org.anoitos.parser.statement.statements.ExpressionStatement
import org.anoitos.parser.statement.statements.TokenStatement

data class BooleanExpression(
    val statements: List<Statement>
) : Expression {
    companion object : ExpressionParser<BooleanExpression> {
        override fun parse(input: List<Token>): Pair<Int, BooleanExpression>? {
            val result = mutableListOf<Statement>()
            var index = 0

            while (index < input.size) {
                val token = input[index]

                if (token.type == TokenType.TRUE || token.type == TokenType.FALSE
                ) {
                    index++
                    result.add(TokenStatement(token))
                } else {
                    val callStatement = CallStatement.parse(input.drop(index))

                    if (callStatement?.second != null) {
                        index += callStatement.first
                        result.add(callStatement.second)
                        continue
                    }

                    val expressionStatement =
                        ExpressionStatement.parse(input.drop(index), listOf(NumberExpression, BooleanExpression))

                    if (expressionStatement?.second != null && expressionStatement.second.expression is IdentifierExpression) {
                        index += expressionStatement.first
                        result.add(expressionStatement.second)
                        continue
                    }

                    break
                }
            }

            return if (result.size == 0 || (result.size == 1 && result[0] is ExpressionStatement && (result[0] as ExpressionStatement).expression is IdentifierExpression)) {
                null
            } else {
                index to BooleanExpression(
                    result
                )
            }
        }
    }
}