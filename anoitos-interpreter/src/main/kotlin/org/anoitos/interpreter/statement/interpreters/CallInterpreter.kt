package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.CallStatement

object CallInterpreter : StatementInterpreter<CallStatement> {
    override fun interpret(statement: CallStatement, context: Context): Any? {
        return context.executeFunction(
            statement.name.value,
            statement.arguments.map { it.interpret(context)!! }
        )
    }
}