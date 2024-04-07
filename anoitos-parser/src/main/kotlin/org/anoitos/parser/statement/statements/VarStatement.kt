package org.anoitos.parser.statement.statements

import org.anoitos.interpreter.context.Context
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class VarStatement(
    val name: Token,
    val value: Statement
) : Statement {
    companion object : StatementParser<VarStatement> {
        override fun parse(input: List<Token>): Pair<Int, VarStatement>? {
            val (size, _, name, _, value, _) = input.search(
                TokenType.VAR,
                TokenType.ID,
                TokenType.EQUAL,
                TokenType.SEARCH_GROUP,
                TokenType.SEMICOLON
            ) ?: return null

            return size to VarStatement(
                name[0],
                Parser.parseStatement(value).second
            )
        }
    }

    override fun interpret(context: Context): Any? {
        context.addVariable(name.value, value.interpret(context)!!)
        return null
    }
}