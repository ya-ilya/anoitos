package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.result.InterpretResult
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.ContinueStatement

object ContinueInterpreter : StatementInterpreter<ContinueStatement> {
    override fun interpret(statement: ContinueStatement, context: Context): Any {
        return InterpretResult.Continue
    }
}