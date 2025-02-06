package org.anoitos.parser.expression

import org.anoitos.parser.expression.expressions.*

object ExpressionRegistry {
    val expressions = listOf(
        StringExpression,
        NumberExpression,
        BooleanExpression,
        CallExpression,
        PathExpression
    )
}