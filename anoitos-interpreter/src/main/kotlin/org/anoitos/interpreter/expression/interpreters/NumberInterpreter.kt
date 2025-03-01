package org.anoitos.interpreter.expression.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.expression.ExpressionInterpreter
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.element.elements.TokenElement
import org.anoitos.parser.expression.expressions.NumberExpression

object NumberInterpreter : ExpressionInterpreter<NumberExpression> {
    private val precedences = mapOf(
        TokenType.PLUS to 1,
        TokenType.MINUS to 1,
        TokenType.MULTIPLY to 2,
        TokenType.DIVIDE to 2,
        TokenType.MOD to 2,
        TokenType.IDIVIDE to 2
    )

    override fun interpret(expression: NumberExpression, context: Context): Any {
        val tokens = mutableListOf<Token>()

        for (element in expression.elements) {
            when (element) {
                is TokenElement -> tokens.add(element.token)

                else -> {
                    val result = element.interpret(context)

                    if (result !is Number) {
                        throw IllegalStateException("Result should be a number value")
                    }

                    tokens.add(
                        Token(
                            TokenType.NUMBER,
                            result.toString(),
                        )
                    )
                }
            }
        }

        return evaluateNumberExpression(tokens)
    }

    private fun evaluateNumberExpression(tokens: List<Token>): Number {
        val valuesStack = mutableListOf<Double>()
        val operatorsStack = mutableListOf<TokenType>()

        fun applyOperator() {
            when (val operator = operatorsStack.removeLast()) {
                TokenType.PLUS -> {
                    val right = valuesStack.removeLast()
                    val left = valuesStack.removeLast()
                    valuesStack.add(left + right)
                }

                TokenType.MINUS -> {
                    val right = valuesStack.removeLast()
                    val left = valuesStack.removeLast()
                    valuesStack.add(left - right)
                }

                TokenType.MULTIPLY -> {
                    val right = valuesStack.removeLast()
                    val left = valuesStack.removeLast()
                    valuesStack.add(left * right)
                }

                TokenType.DIVIDE -> {
                    val right = valuesStack.removeLast()
                    val left = valuesStack.removeLast()
                    valuesStack.add(left / right)
                }

                TokenType.MOD -> {
                    val right = valuesStack.removeLast()
                    val left = valuesStack.removeLast()
                    valuesStack.add(left % right)
                }

                TokenType.IDIVIDE -> {
                    val right = valuesStack.removeLast().toInt()
                    val left = valuesStack.removeLast().toInt()
                    valuesStack.add(left.div(right).toDouble())
                }

                else -> throw IllegalStateException("Unexpected operator: $operator")
            }
        }

        for (token in tokens) {
            when (token.type) {
                TokenType.NUMBER -> valuesStack.add(token.value.toDouble())
                TokenType.LPAREN -> operatorsStack.add(token.type)
                TokenType.RPAREN -> {
                    while (operatorsStack.isNotEmpty() && operatorsStack.last() != TokenType.LPAREN) {
                        applyOperator()
                    }
                    if (operatorsStack.isEmpty() || operatorsStack.removeLast() != TokenType.LPAREN) {
                        throw IllegalStateException("Mismatched parentheses")
                    }
                }

                TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.MOD, TokenType.IDIVIDE -> {
                    while (
                        operatorsStack.isNotEmpty() &&
                        operatorsStack.last() != TokenType.LPAREN &&
                        precedences[operatorsStack.last()]!! >= precedences[token.type]!!
                    ) {
                        applyOperator()
                    }

                    operatorsStack.add(token.type)
                }

                else -> throw IllegalStateException("Unexpected token: ${token.type}")
            }
        }

        while (operatorsStack.isNotEmpty()) {
            applyOperator()
        }

        if (valuesStack.size != 1) {
            throw IllegalStateException("Invalid number expression")
        }

        return valuesStack.single()
    }
}