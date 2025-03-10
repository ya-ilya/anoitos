package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.InterpretResult
import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.ForStatement

object ForInterpreter : StatementInterpreter<ForStatement> {
    override fun interpret(statement: ForStatement, context: Context): Any? {
        var index = (statement.from.interpret(context) as Number).toInt()
        val to = (statement.to.interpret(context) as Number).toInt()
        while (index <= to) {
            val forContext = Context(context)
            forContext.addVariable(statement.identifier.value, index)
            when (val result = statement.body.interpret(forContext)) {
                InterpretResult.Break -> break
                InterpretResult.Continue -> continue
                is InterpretResult.Return -> return result
            }
            index = forContext.getVariable(statement.identifier.value)!!.value as Int + 1
        }

        return null
    }
}