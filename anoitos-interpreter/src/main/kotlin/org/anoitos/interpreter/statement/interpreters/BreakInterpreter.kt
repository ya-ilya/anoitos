package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.InterpretResult
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.BreakStatement

object BreakInterpreter : StatementInterpreter<BreakStatement> {
    override fun interpret(statement: BreakStatement, context: Context): Any {
        return InterpretResult.Break
    }
}