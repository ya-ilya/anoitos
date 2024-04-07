package org.anoitos.parser.statement

import org.anoitos.interpreter.context.Context

interface Statement {
    fun interpret(context: Context): Any?
}