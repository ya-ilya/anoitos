package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class WhileStatement(
    val condition: Statement,
    val body: BlockStatement
) : Statement {
    companion object : StatementParser<WhileStatement> {
        override fun parse(input: List<Token>): Pair<Int, WhileStatement>? {
            val (size, result) = input.search(
                TokenType.WHILE,
                TokenType.LPAREN,
                TokenType.SEARCH_GROUP,
                TokenType.RPAREN,
                TokenType.LBRACE,
                TokenType.SEARCH_GROUP,
                TokenType.RBRACE
            ) ?: return null
            val (_, _, condition, _, _, body, _) = result

            return size to WhileStatement(
                Parser.parseStatement(condition).second,
                BlockStatement.parse(body).second
            )
        }
    }
}