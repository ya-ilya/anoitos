package org.anoitos.parser.statement.statements

import org.anoitos.interpreter.context.Context
import org.anoitos.lexer.token.Token
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.expression.ExpressionParser
import org.anoitos.parser.expression.ExpressionRegistry
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class ExpressionStatement(val expression: Expression) : Statement {
    companion object : StatementParser<ExpressionStatement> {
        override fun parse(input: List<Token>): Pair<Int, ExpressionStatement>? {
            return parse(input, emptyList())
        }

        fun parse(input: List<Token>, exclude: List<ExpressionParser<*>>): Pair<Int, ExpressionStatement>? {
            for (expression in ExpressionRegistry.expressions.filter { !exclude.contains(it) }) {
                val result = expression.parse(input)

                if (result != null) {
                    return result.first to ExpressionStatement(result.second)
                }
            }

            return null
        }
    }

    override fun interpret(context: Context): Any? {
        return expression.interpret(context)
    }
}