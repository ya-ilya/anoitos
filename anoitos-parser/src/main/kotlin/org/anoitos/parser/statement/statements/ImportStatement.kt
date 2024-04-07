package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class ImportStatement(
    val paths: List<String>
) : Statement {
    companion object : StatementParser<ImportStatement> {
        override fun parse(input: List<Token>): Pair<Int, ImportStatement>? {
            val (size, result) = input.search(
                TokenType.IMPORT,
                TokenType.SEARCH_GROUP,
                TokenType.SEMICOLON
            ) ?: return null
            val (_, paths, _) = result

            if (paths.any { it.type != TokenType.STRING && it.type != TokenType.COMMA }) {
                return null
            }

            return size to ImportStatement(
                paths.filter { it.type != TokenType.COMMA }.map { it.value }
            )
        }
    }
}