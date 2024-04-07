package org.anoitos.interpreter.expression.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.expression.ExpressionInterpreter
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.expression.expressions.NumberExpression
import org.anoitos.parser.statement.statements.CallStatement
import org.anoitos.parser.statement.statements.ExpressionStatement
import org.anoitos.parser.statement.statements.TokenStatement

object NumberInterpreter : ExpressionInterpreter<NumberExpression> {
    override fun interpret(expression: NumberExpression, context: Context): Any {
        val tokens = mutableListOf<Token>()

        for (statement in expression.statements) {
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