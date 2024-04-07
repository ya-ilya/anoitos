package org.anoitos.interpreter.expression

import org.anoitos.interpreter.context.Context
import org.anoitos.parser.expression.Expression

interface ExpressionInterpreter<T : Expression> {
    fun interpret(expression: T, context: Context): Any?
}