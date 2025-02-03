package org.anoitos.parser.expression.expressions

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenGroup
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.expression.ExpressionParser
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.statements.CallStatement
import org.anoitos.parser.statement.statements.ExpressionStatement
import org.anoitos.parser.statement.statements.TokenStatement

data class NumberExpression(
    val statements: List<Statement>
) : Expression {
    companion object : ExpressionParser<NumberExpression> {
        override fun parse(input: List<Token>): Pair<Int, NumberExpression>? {
            val result = mutableListOf<Statement>()
            var index = 0

            while (index < input.size) {
                val token = input[index]

                when (token.type) {
                    TokenType.NUMBER -> {
                        index++
                        result.add(TokenStatement(token))
                    }

                    TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.MOD, TokenType.IDIVIDE -> {
                        index++
                        result.add(TokenStatement(token))
                    }

                    TokenType.LPAREN -> {
                        val subExpression = parseSubExpression(input.drop(index + 1)) ?: return null
                        index += subExpression.first + 2 // +2 to account for the parentheses
                        result.add(ExpressionStatement(subExpression.second))
                    }

                    else -> {
                        val statementInput = input.drop(index)
                        val callStatement = CallStatement.parse(statementInput)

                        if (callStatement?.second != null) {
                            index += callStatement.first
                            result.add(callStatement.second)
                            continue
                        }

                        val expressionStatement = ExpressionStatement.parse(
                            statementInput,
                            listOf(
                                BooleanExpression,
                                NumberExpression,
                                StringExpression
                            )
                        )

                        if (expressionStatement?.second != null && expressionStatement.second.expression is PathExpression) {
                            index += expressionStatement.first
                            result.add(expressionStatement.second)
                            continue
                        }

                        return null
                    }
                }
            }

            return if (result.isEmpty() || result.none { (it is TokenStatement && it.token.type.group == TokenGroup.NUMERIC) || (it is ExpressionStatement && it.expression is NumberExpression) }) {
                null
            } else {
                index to NumberExpression(result)
            }
        }

        private fun parseSubExpression(input: List<Token>): Pair<Int, NumberExpression>? {
            val result = mutableListOf<Statement>()
            var index = 0

            while (index < input.size) {
                val token = input[index]

                when (token.type) {
                    TokenType.NUMBER -> {
                        index++
                        result.add(TokenStatement(token))
                    }

                    TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.MOD, TokenType.IDIVIDE -> {
                        index++
                        result.add(TokenStatement(token))
                    }

                    TokenType.LPAREN -> {
                        val subExpression = parseSubExpression(input.drop(index + 1)) ?: return null
                        index += subExpression.first + 2 // +2 to account for the parentheses
                        result.add(ExpressionStatement(subExpression.second))
                    }

                    TokenType.RPAREN -> {
                        return index to NumberExpression(result)
                    }

                    else -> {
                        val statementInput = input.drop(index)
                        val callStatement = CallStatement.parse(statementInput)

                        if (callStatement?.second != null) {
                            index += callStatement.first
                            result.add(callStatement.second)
                            continue
                        }

                        val expressionStatement = ExpressionStatement.parse(
                            statementInput,
                            listOf(
                                BooleanExpression,
                                NumberExpression,
                                StringExpression
                            )
                        )

                        if (expressionStatement?.second != null && expressionStatement.second.expression is PathExpression) {
                            index += expressionStatement.first
                            result.add(expressionStatement.second)
                            continue
                        }

                        break
                    }
                }
            }

            return null
        }
    }
}