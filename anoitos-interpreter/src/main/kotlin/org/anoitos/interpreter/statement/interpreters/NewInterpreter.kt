package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.NewStatement

object NewInterpreter : StatementInterpreter<NewStatement> {
    override fun interpret(statement: NewStatement, context: Context): Any {
        val className = statement.name.value
        val classContext = context.getClass(className) ?: throw IllegalStateException("Class '${className}' not found")

        val instanceContext = Context()

        for (function in classContext.functions) {
            instanceContext.addFunction(function)
        }

        for (variable in classContext.variables) {
            instanceContext.addVariable(variable.key, variable.value)
        }

        return instanceContext
    }
}