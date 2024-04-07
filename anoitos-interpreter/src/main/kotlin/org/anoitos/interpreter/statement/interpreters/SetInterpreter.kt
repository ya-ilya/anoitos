package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.SetStatement

object SetInterpreter : StatementInterpreter<SetStatement> {
    override fun interpret(statement: SetStatement, context: Context): Any? {
        context.setVariable(statement.name.identifier, statement.value.interpret(context)!!)
        return null
    }
}