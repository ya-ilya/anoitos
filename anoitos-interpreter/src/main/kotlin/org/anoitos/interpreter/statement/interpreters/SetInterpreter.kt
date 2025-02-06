package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.element.TokenElement
import org.anoitos.parser.statement.statements.SetStatement

object SetInterpreter : StatementInterpreter<SetStatement> {
    override fun interpret(statement: SetStatement, context: Context): Any? {
        var currentContext = context

        for (pathStatement in statement.pathExpression.elements) {
            if (pathStatement is TokenElement) {
                val variable = currentContext.getVariable(pathStatement.token.value)

                if (variable != null) {
                    if (variable is Context) {
                        currentContext = variable
                        continue
                    } else {
                        currentContext.setVariable(pathStatement.token.value, statement.value.interpret(context)!!)
                        break
                    }
                }

                val classContext = currentContext.getClass(pathStatement.token.value)

                if (classContext != null) {
                    currentContext = classContext
                    continue
                }

                throw IllegalStateException("Variable or class '${pathStatement.token.value}' not found")
            }
        }

        return null
    }
}