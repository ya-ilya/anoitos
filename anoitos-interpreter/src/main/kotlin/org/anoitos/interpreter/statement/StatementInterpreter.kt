package org.anoitos.interpreter.statement

import org.anoitos.interpreter.context.Context
import org.anoitos.parser.statement.Statement

interface StatementInterpreter<T : Statement> {
    fun interpret(statement: T, context: Context): Any?
}