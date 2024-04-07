package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.result.InterpretResult
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.DoStatement

object DoInterpreter : StatementInterpreter<DoStatement> {
    override fun interpret(statement: DoStatement, context: Context): Any? {
        do {
            when (val result = statement.body.interpret(Context(context))) {
                InterpretResult.Break -> break
                InterpretResult.Continue -> continue
                is InterpretResult.Return -> return result
                else -> {}
            }
        } while (statement.condition.interpret(context) as Boolean)

        return null
    }
}