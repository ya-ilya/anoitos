package org.anoitos.parser.statement.statements

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.result.InterpretResult
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class DoStatement(val condition: Statement, val body: BlockStatement) : Statement {
    companion object : StatementParser<DoStatement> {
        override fun parse(input: List<Token>): Pair<Int, DoStatement>? {
            val (size, _, _, body, _, _, _, condition, _) = input.search(
                TokenType.DO,
                TokenType.LBRACE,
                TokenType.SEARCH_GROUP,
                TokenType.RBRACE,
                TokenType.WHILE,
                TokenType.LPAREN,
                TokenType.SEARCH_GROUP,
                TokenType.RPAREN,
            ) ?: return null

            return size to DoStatement(
                Parser.parseStatement(condition).second,
                BlockStatement.parse(body).second
            )
        }
    }

    override fun interpret(context: Context): Any? {
        do {
            when (val result = body.interpret(Context(context))) {
                InterpretResult.Break -> break
                InterpretResult.Continue -> continue
                is InterpretResult.Return -> return result
                else -> {}
            }
        } while (condition.interpret(context) as Boolean)

        return null
    }
}