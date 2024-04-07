package org.anoitos.parser.statement.statements

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.result.InterpretResult
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

class BreakStatement : Statement {
    companion object : StatementParser<BreakStatement> {
        override fun parse(input: List<Token>): Pair<Int, BreakStatement>? {
            val (size, _) = input.search(
                TokenType.BREAK,
                TokenType.SEMICOLON
            ) ?: return null

            return size to BreakStatement()
        }
    }

    override fun interpret(context: Context): InterpretResult {
        return InterpretResult.Break
    }
}