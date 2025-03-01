package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.InterpretResult
import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.WhileStatement

object WhileInterpreter : StatementInterpreter<WhileStatement> {
    override fun interpret(statement: WhileStatement, context: Context): Any? {
        while (statement.condition.interpret(context) as Boolean) {
            when (val result = statement.body.interpret(Context(context))) {
                InterpretResult.Break -> break
                InterpretResult.Continue -> continue
                is InterpretResult.Return -> return result
            }
        }

        return null
    }
}