package org.anoitos.interpreter.expression.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.expression.ExpressionInterpreter
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.element.elements.TokenElement
import org.anoitos.parser.expression.expressions.BooleanExpression

object BooleanInterpreter : ExpressionInterpreter<BooleanExpression> {
    override fun interpret(expression: BooleanExpression, context: Context): Any {
        val tokens = mutableListOf<Token>()

        for (element in expression.elements) {
            when (element) {
                is TokenElement -> tokens.add(element.token)

                else -> {
                    val result = (element.interpret(context) as Boolean)

                    tokens.add(
                        Token(
                            if (result) TokenType.TRUE else TokenType.FALSE,
                            result.toString(),
                        )
                    )
                }
            }
        }

        return evaluateBooleanExpression(tokens)
    }

    private fun evaluateBooleanExpression(tokens: List<Token>): Boolean {
        val valuesStack = mutableListOf<Boolean>()
        val operatorsStack = mutableListOf<TokenType>()

        fun applyOperator() {
            when (val operator = operatorsStack.removeLast()) {
                TokenType.NOT -> {
                    val value = valuesStack.removeLast()
                    valuesStack.add(!value)
                }

                TokenType.AND -> {
                    val right = valuesStack.removeLast()
                    val left = valuesStack.removeLast()
                    valuesStack.add(left && right)
                }

                TokenType.OR -> {
                    val right = valuesStack.removeLast()
                    val left = valuesStack.removeLast()
                    valuesStack.add(left || right)
                }

                TokenType.EQUALS -> {
                    val right = valuesStack.removeLast()
                    val left = valuesStack.removeLast()
                    valuesStack.add(left == right)
                }

                else -> throw IllegalStateException("Unexpected operator: $operator")
            }
        }

        for (token in tokens) {
            when (token.type) {
                TokenType.TRUE -> valuesStack.add(true)
                TokenType.FALSE -> valuesStack.add(false)
                TokenType.NOT, TokenType.AND, TokenType.OR, TokenType.EQUALS -> operatorsStack.add(token.type)
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
            throw IllegalStateException("Invalid boolean expression")
        }

        return valuesStack.single()
    }
}