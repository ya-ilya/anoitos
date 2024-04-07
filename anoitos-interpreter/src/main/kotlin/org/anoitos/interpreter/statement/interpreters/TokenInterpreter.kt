package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.TokenStatement

object TokenInterpreter : StatementInterpreter<TokenStatement> {
    override fun interpret(statement: TokenStatement, context: Context): Any? {
        return null
    }
}