package org.anoitos.interpreter.expression.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.expression.ExpressionInterpreter
import org.anoitos.parser.expression.expressions.StringExpression

object StringInterpreter : ExpressionInterpreter<StringExpression> {
    override fun interpret(expression: StringExpression, context: Context): Any {
        return expression.value
    }
}