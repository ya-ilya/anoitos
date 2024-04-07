package org.anoitos.parser.expression

import org.anoitos.interpreter.context.Context

interface Expression {
    fun interpret(context: Context): Any?
}