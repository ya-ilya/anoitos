package org.anoitos.interpreter.expression

import org.anoitos.interpreter.expression.interpreters.BooleanInterpreter
import org.anoitos.interpreter.expression.interpreters.IdentifierInterpreter
import org.anoitos.interpreter.expression.interpreters.NumberInterpreter
import org.anoitos.interpreter.expression.interpreters.StringInterpreter
import org.anoitos.parser.expression.expressions.BooleanExpression
import org.anoitos.parser.expression.expressions.IdentifierExpression
import org.anoitos.parser.expression.expressions.NumberExpression
import org.anoitos.parser.expression.expressions.StringExpression

object ExpressionInterpreterRegistry {
    val interpreters = mapOf(
        BooleanExpression::class to BooleanInterpreter,
        IdentifierExpression::class to IdentifierInterpreter,
        NumberExpression::class to NumberInterpreter,
        StringExpression::class to StringInterpreter
    )
}