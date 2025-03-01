package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.InterpretResult
import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.ForEachStatement

object ForEachInterpreter : StatementInterpreter<ForEachStatement> {
    override fun interpret(statement: ForEachStatement, context: Context): Any? {
        val valueResult = statement.value.interpret(context)

        if (valueResult is Array<*>) {
            for (item in valueResult) {
                val forContext = Context(context)
                forContext.addVariable(statement.identifier.value, item!!)
                when (val result = statement.body.interpret(forContext)) {
                    InterpretResult.Break -> break
                    InterpretResult.Continue -> continue
                    is InterpretResult.Return -> return result
                }
            }
        } else if (valueResult is Iterable<*>) {
            for (item in valueResult) {
                val forContext = Context(context)
                forContext.addVariable(statement.identifier.value, item!!)
                when (val result = statement.body.interpret(forContext)) {
                    InterpretResult.Break -> break
                    InterpretResult.Continue -> continue
                    is InterpretResult.Return -> return result
                }
            }
        }

        return null
    }
}