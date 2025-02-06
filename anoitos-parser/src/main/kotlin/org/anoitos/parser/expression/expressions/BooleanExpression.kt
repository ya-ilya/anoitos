package org.anoitos.parser.expression.expressions

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenGroup
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.element.ParserElement
import org.anoitos.parser.element.TokenElement
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.expression.ExpressionParser

data class BooleanExpression(
    val elements: List<ParserElement>
) : Expression {
    companion object : ExpressionParser<BooleanExpression> {
        override fun parse(input: List<Token>): Pair<Int, BooleanExpression>? {
            val result = mutableListOf<ParserElement>()
            var index = 0

            while (index < input.size) {
                val token = input[index]

                when (token.type) {
                    TokenType.SEMICOLON -> {
                        break
                    }

                    TokenType.TRUE, TokenType.FALSE -> {
                        index++
                        result.add(TokenElement(token))
                    }

                    TokenType.AND, TokenType.OR, TokenType.NOT, TokenType.EQUALS -> {
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

                        if (expression?.second != null) {
                            index += expression.first
                            result.add(expression.second)
                            continue
                        }

                        return null
                    }
                }
            }

            return if (result.isEmpty() || result.none { (it is TokenElement && it.token.type.group == TokenGroup.LOGICAL) || (it is BooleanExpression) }) {
                null
            } else {
                index to BooleanExpression(result)
            }
        }

        private fun parseSubExpression(input: List<Token>): Pair<Int, BooleanExpression>? {
            val result = mutableListOf<ParserElement>()
            var index = 0

            while (index < input.size) {
                val token = input[index]

                when (token.type) {
                    TokenType.TRUE, TokenType.FALSE -> {
                        index++
                        result.add(TokenElement(token))
                    }

                    TokenType.AND, TokenType.OR, TokenType.NOT, TokenType.EQUALS -> {
                        index++
                        result.add(TokenElement(token))
                    }

                    TokenType.LPAREN -> {
                        val subExpression = parseSubExpression(input.drop(index + 1)) ?: return null
                        index += subExpression.first + 2 // +2 to account for the parentheses
                        result.add(subExpression.second)
                    }

                    TokenType.RPAREN -> {
                        return index to BooleanExpression(result)
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

                        if (expression?.second != null) {
                            index += expression.first
                            result.add(expression.second)
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