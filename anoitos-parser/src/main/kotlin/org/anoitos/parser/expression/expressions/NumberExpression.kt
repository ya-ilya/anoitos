package org.anoitos.parser.expression.expressions

import org.anoitos.interpreter.context.Context
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.expression.ExpressionParser
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.statements.CallStatement
import org.anoitos.parser.statement.statements.ExpressionStatement
import org.anoitos.parser.statement.statements.TokenStatement

data class NumberExpression(val statements: List<Statement>) : Expression {
    companion object : ExpressionParser<NumberExpression> {
        override fun parse(input: List<Token>): Pair<Int, NumberExpression>? {
            val result = mutableListOf<Statement>()
            var index = 0

            while (index < input.size) {
                val token = input[index]

                if (token.type == TokenType.INT || token.type == TokenType.DOUBLE
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
                index to NumberExpression(result)
            }
        }
    }

    override fun interpret(context: Context): Any {
        val tokens = mutableListOf<Token>()

        for (statement in statements) {
            when (statement) {
                is TokenStatement -> tokens.add(statement.token)

                is CallStatement, is ExpressionStatement -> {
                    val result = statement.interpret(context)
                    val type = if (result is Double) TokenType.DOUBLE else TokenType.INT

                    tokens.add(Token(type, result.toString()))
                }
            }
        }

        return if (tokens[0].type == TokenType.DOUBLE) tokens[0].value.toDouble() else tokens[0].value.toInt()
    }
}