package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.IfStatement

object IfInterpreter : StatementInterpreter<IfStatement> {
    override fun interpret(statement: IfStatement, context: Context): Any? {
        if (statement.ifCondition.interpret(context) as Boolean) {
            return statement.ifBlock.interpret(Context(context))
        } else {
            for ((elIfCondition, elIfBlock) in statement.elIfs) {
                if (elIfCondition.interpret(context) as Boolean) {
                    return elIfBlock.interpret(Context(context))
                }
            }

            if (statement.elseBlock != null) {
                return statement.elseBlock!!.interpret(Context(context))
            }
        }

        return null
    }
}