package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.ExpressionStatement

object ExpressionInterpreter : StatementInterpreter<ExpressionStatement> {
    override fun interpret(statement: ExpressionStatement, context: Context): Any? {
        return statement.expression.interpret(context)
    }
}