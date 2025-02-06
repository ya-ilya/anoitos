package org.anoitos.parser

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.element.EmptyElement
import org.anoitos.parser.element.ParserElement
import org.anoitos.parser.expression.ExpressionParser
import org.anoitos.parser.expression.ExpressionRegistry
import org.anoitos.parser.statement.StatementRegistry

object Parser {
    fun parse(input: List<Token>): List<ParserElement> {
        var current = 0
        val elements = mutableListOf<ParserElement>()

        while (current < input.size) {
            val element = parseElement(input.drop(current))?.also { (size, element) ->
                current += size

                if (element !is EmptyElement) {
                    elements.add(element)
                }
            }

            if (element != null) continue

            throw IllegalStateException("No valid statement or expression found for the given input")
        }

        return elements
    }

    fun parseElement(input: List<Token>): Pair<Int, ParserElement>? {
        val statementResult = parseStatement(input)

        if (statementResult != null) return statementResult

        val expressionResult = parseExpression(input)

        if (expressionResult != null) return expressionResult

        return null
    }

    fun parseStatement(input: List<Token>): Pair<Int, ParserElement>? {
        if (input[0].type == TokenType.SEMICOLON) {
            return 1 to EmptyElement()
        }

        for (statement in StatementRegistry.statements) {
            val result = statement.parse(input)

            if (result != null) {
                return result
            }
        }

        return null
    }

    fun parseExpression(
        input: List<Token>,
        exclude: List<ExpressionParser<*>> = emptyList()
    ): Pair<Int, ParserElement>? {
        for (expression in ExpressionRegistry.expressions.filter { !exclude.contains(it) }) {
            val result = expression.parse(input)

            if (result != null) {
                return result
            }
        }

        return null
    }
}