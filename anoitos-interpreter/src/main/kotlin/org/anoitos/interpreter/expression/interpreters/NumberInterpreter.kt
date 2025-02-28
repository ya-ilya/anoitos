package org.anoitos.interpreter.expression.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.expression.ExpressionInterpreter
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.element.elements.TokenElement
import org.anoitos.parser.expression.expressions.NumberExpression

object NumberInterpreter : ExpressionInterpreter<NumberExpression> {
    override fun interpret(expression: NumberExpression, context: Context): Any {
        val tokens = mutableListOf<Token>()

        for (element in expression.elements) {
            when (element) {
                is TokenElement -> tokens.add(element.token)

                else -> {
                    val result = (element.interpret(context) as Number)

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
                TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.MOD, TokenType.IDIVIDE -> operatorsStack.add(
                    token.type
                )

                TokenType.LPAREN -> operatorsStack.add(token.type)
                TokenType.RPAREN -> {
                    while (operatorsStack.isNotEmpty() && operatorsStack.last() != TokenType.LPAREN) {
                        applyOperator()
                    }
                    if (operatorsStack.isEmpty() || operatorsStack.removeLast() != TokenType.LPAREN) {
                        throw IllegalStateException("Mismatched parentheses")
                    }
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