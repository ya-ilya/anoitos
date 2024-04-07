package org.anoitos.interpreter.expression.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.expression.ExpressionInterpreter
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.expression.expressions.BooleanExpression
import org.anoitos.parser.statement.statements.CallStatement
import org.anoitos.parser.statement.statements.ExpressionStatement
import org.anoitos.parser.statement.statements.TokenStatement

object BooleanInterpreter : ExpressionInterpreter<BooleanExpression> {
    override fun interpret(expression: BooleanExpression, context: Context): Any {
        val tokens = mutableListOf<Token>()

        for (statement in expression.statements) {
            when (statement) {
                is TokenStatement -> tokens.add(statement.token)

                is CallStatement, is ExpressionStatement -> {
                    val result = (statement.interpret(context) as Boolean)

                    tokens.add(
                        Token(
                            if (result) TokenType.TRUE else TokenType.FALSE,
                            result.toString(),
                        )
                    )
                }
            }
        }

        return tokens[0].value.toBooleanStrict()
    }
}