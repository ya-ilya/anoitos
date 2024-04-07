package org.anoitos.interpreter

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.result.InterpretResult
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.statements.BreakStatement
import org.anoitos.parser.statement.statements.ContinueStatement

object Interpreter {
    fun interpret(statements: List<Statement>, context: Context): InterpretResult? {
        for (statement in statements) {
            when (statement) {
                is BreakStatement -> return InterpretResult.Break
                is ContinueStatement -> return InterpretResult.Continue
            }

            val result = statement.interpret(context)

            if (result is InterpretResult.Return) {
                return result
            }
        }

        return null
    }
}