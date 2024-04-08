package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.SetStatement
import org.anoitos.parser.statement.statements.TokenStatement

object SetInterpreter : StatementInterpreter<SetStatement> {
    override fun interpret(statement: SetStatement, context: Context): Any? {
        var currentContext = context

        for (pathStatement in statement.pathExpression.statements) {
            if (pathStatement is TokenStatement) {
                val variable = currentContext.getVariable(pathStatement.token.value)

                if (variable != null) {
                    currentContext.setVariable(pathStatement.token.value, statement.value.interpret(context)!!)
                    break
                }

                val classContext = currentContext.getClass(pathStatement.token.value)

                if (classContext != null) {
                    currentContext = classContext
                } else {
                    throw Exception()
                }
            }
        }

        return null
    }
}