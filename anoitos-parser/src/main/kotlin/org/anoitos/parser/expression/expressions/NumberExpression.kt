package org.anoitos.parser.expression.expressions

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenGroup
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.ParserResult
import org.anoitos.parser.element.ParserElement
import org.anoitos.parser.element.elements.TokenElement
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.expression.ExpressionParser

data class NumberExpression(
    val elements: List<ParserElement>
) : Expression {
    companion object : ExpressionParser<NumberExpression> {
        override fun parse(input: List<Token>): ParserResult<NumberExpression>? {
            val result = mutableListOf<ParserElement>()
            var index = 0

            while (index < input.size) {
                val token = input[index]

                when (token.type) {
                    TokenType.NUMBER -> {
                        index++
                        result.add(TokenElement(token))
                    }

                    TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.MOD, TokenType.IDIVIDE -> {
                        index++
                        result.add(TokenElement(token))
                    }

                    TokenType.LPAREN -> {
                        val subExpression = parseSubExpression(input.drop(index + 1)) ?: return null
                        index += subExpression.first + 2 // +2 to account for the parentheses
                        result.add(subExpression.second)
                    }

                    else -> {
                        val statementInput = input.drop(index)

                        val expression = Parser.parseExpression(
                            statementInput,
                            listOf(
                                BooleanExpression,
                                NumberExpression,
                                StringExpression
                            )
                        )

                        if (expression?.element != null) {
                            index += expression.size
                            result.add(expression.element)
                            continue
                        }

                        return null
                    }
                }
            }

            return if (result.isEmpty() || result.none { (it is TokenElement && it.token.type.group == TokenGroup.NUMERIC) || (it is NumberExpression) }) {
                null
            } else {
                ParserResult(
                    index,
                    NumberExpression(result)
                )
            }
        }

        private fun parseSubExpression(input: List<Token>): Pair<Int, NumberExpression>? {
            val result = mutableListOf<ParserElement>()
            var index = 0

            while (index < input.size) {
                val token = input[index]

                when (token.type) {
                    TokenType.NUMBER -> {
                        index++
                        result.add(TokenElement(token))
                    }

                    TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.MOD, TokenType.IDIVIDE -> {
                        index++
                        result.add(TokenElement(token))
                    }

                    TokenType.LPAREN -> {
                        val subExpression = parseSubExpression(input.drop(index + 1)) ?: return null
                        index += subExpression.first + 2 // +2 to account for the parentheses
                        result.add(subExpression.second)
                    }

                    TokenType.RPAREN -> {
                        return index to NumberExpression(result)
                    }

                    else -> {
                        val statementInput = input.drop(index)

                        val expression = Parser.parseExpression(
                            statementInput,
                            listOf(
                                BooleanExpression,
                                NumberExpression,
                                StringExpression
                            )
                        )

                        if (expression?.element != null) {
                            index += expression.size
                            result.add(expression.element)
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