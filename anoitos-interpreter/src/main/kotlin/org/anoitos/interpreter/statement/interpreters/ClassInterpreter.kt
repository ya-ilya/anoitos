package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.ClassStatement

object ClassInterpreter : StatementInterpreter<ClassStatement> {
    override fun interpret(statement: ClassStatement, context: Context): Any? {
        context.addClass(statement)
        return null
    }
}