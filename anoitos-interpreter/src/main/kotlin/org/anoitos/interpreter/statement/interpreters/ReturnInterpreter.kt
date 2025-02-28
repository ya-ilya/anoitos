package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.InterpretResult
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.ReturnStatement

object ReturnInterpreter : StatementInterpreter<ReturnStatement> {
    override fun interpret(statement: ReturnStatement, context: Context): Any {
        return InterpretResult.Return(statement.value.interpret(context))
    }
}