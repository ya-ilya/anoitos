package org.anoitos.interpreter.expression.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.expression.ExpressionInterpreter
import org.anoitos.parser.expression.expressions.IdentifierExpression

object IdentifierInterpreter : ExpressionInterpreter<IdentifierExpression> {
    override fun interpret(expression: IdentifierExpression, context: Context): Any? {
        return context.getVariable(expression.identifier)
    }
}