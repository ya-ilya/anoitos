package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.EmptyStatement

object EmptyInterpreter : StatementInterpreter<EmptyStatement> {
    override fun interpret(statement: EmptyStatement, context: Context): Any? {
        return null
    }
}