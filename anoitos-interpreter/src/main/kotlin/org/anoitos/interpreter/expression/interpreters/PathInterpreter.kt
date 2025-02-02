package org.anoitos.interpreter.expression.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.expression.ExpressionInterpreter
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.parser.expression.expressions.PathExpression
import org.anoitos.parser.statement.statements.CallStatement
import org.anoitos.parser.statement.statements.TokenStatement

object PathInterpreter : ExpressionInterpreter<PathExpression> {
    override fun interpret(expression: PathExpression, context: Context): Any? {
        var currentValue: Any? = null

        for (statement in expression.statements) {
            val currentContext = if (currentValue is Context) currentValue else context

            when (statement) {
                is TokenStatement -> {
                    val variable = currentContext.getVariable(statement.token.value)

                    if (variable != null) {
                        currentValue = variable
                        break
                    }

                    val classContext = currentContext.getClass(statement.token.value)

                    if (classContext != null) {
                        currentValue = classContext
                        break
                    }

                    throw IllegalStateException("Unknown token type")
                }

                is CallStatement -> {
                    currentValue = currentContext.executeFunction(
                        statement.name.value,
                        statement.arguments.map { it.interpret(context)!! }
                    )
                }
            }
        }

        return currentValue
    }
}