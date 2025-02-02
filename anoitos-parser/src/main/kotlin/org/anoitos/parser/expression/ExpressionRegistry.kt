package org.anoitos.parser.expression

import org.anoitos.parser.expression.expressions.BooleanExpression
import org.anoitos.parser.expression.expressions.NumberExpression
import org.anoitos.parser.expression.expressions.PathExpression
import org.anoitos.parser.expression.expressions.StringExpression

object ExpressionRegistry {
    val expressions = listOf(
        StringExpression,
        NumberExpression,
        BooleanExpression,
        PathExpression
    )
}