package org.anoitos.interpreter.expression.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.expression.ExpressionInterpreter
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.parser.expression.expressions.CallExpression

object CallInterpreter : ExpressionInterpreter<CallExpression> {
    override fun interpret(expression: CallExpression, context: Context): Any? {
        return context.executeFunction(
            expression.name.value,
            expression.arguments.map { it.interpret(context)!! }
        )
    }
}