package org.anoitos.parser.statement.statements

import org.anoitos.interpreter.context.Context
import org.anoitos.lexer.token.Token
import org.anoitos.parser.statement.Statement

data class TokenStatement(val token: Token) : Statement {
    override fun interpret(context: Context): Any? {
        return null
    }
}