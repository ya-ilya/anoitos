package org.anoitos.parser.statement.statements

import org.anoitos.interpreter.context.Context
import org.anoitos.parser.statement.Statement

class EmptyStatement : Statement {
    override fun interpret(context: Context): Any? {
        return null
    }
}