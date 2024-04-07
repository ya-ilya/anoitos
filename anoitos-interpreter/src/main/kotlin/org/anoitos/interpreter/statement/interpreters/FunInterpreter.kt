package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.FunStatement

object FunInterpreter : StatementInterpreter<FunStatement> {
    override fun interpret(statement: FunStatement, context: Context): Any? {
        context.addFunction(statement)
        return null
    }
}