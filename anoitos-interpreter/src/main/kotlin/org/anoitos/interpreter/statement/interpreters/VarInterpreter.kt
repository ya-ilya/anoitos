package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.VarStatement

object VarInterpreter : StatementInterpreter<VarStatement> {
    override fun interpret(statement: VarStatement, context: Context): Any? {
        context.addVariable(statement.name.value, statement.value.interpret(context)!!)
        return null
    }
}