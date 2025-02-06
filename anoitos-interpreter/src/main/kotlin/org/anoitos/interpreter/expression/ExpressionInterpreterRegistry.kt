package org.anoitos.interpreter.expression

import org.anoitos.interpreter.expression.interpreters.*
import org.anoitos.parser.expression.expressions.*

object ExpressionInterpreterRegistry {
    val interpreters = mapOf(
        BooleanExpression::class to BooleanInterpreter,
        NumberExpression::class to NumberInterpreter,
        PathExpression::class to PathInterpreter,
        StringExpression::class to StringInterpreter,
        CallExpression::class to CallInterpreter
    )
}