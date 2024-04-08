package org.anoitos.interpreter.expression

import org.anoitos.interpreter.expression.interpreters.BooleanInterpreter
import org.anoitos.interpreter.expression.interpreters.NumberInterpreter
import org.anoitos.interpreter.expression.interpreters.PathInterpreter
import org.anoitos.interpreter.expression.interpreters.StringInterpreter
import org.anoitos.parser.expression.expressions.BooleanExpression
import org.anoitos.parser.expression.expressions.NumberExpression
import org.anoitos.parser.expression.expressions.PathExpression
import org.anoitos.parser.expression.expressions.StringExpression

object ExpressionInterpreterRegistry {
    val interpreters = mapOf(
        BooleanExpression::class to BooleanInterpreter,
        NumberExpression::class to NumberInterpreter,
        PathExpression::class to PathInterpreter,
        StringExpression::class to StringInterpreter
    )
}