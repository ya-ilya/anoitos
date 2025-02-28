package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.ParserResult
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class ImportStatement(
    val paths: List<String>
) : Statement {
    companion object : StatementParser<ImportStatement> {
        override fun parse(input: List<Token>): ParserResult<ImportStatement>? {
            val (size, _, paths, _) = input.search(
                TokenType.IMPORT,
                TokenType.SEARCH_GROUP,
                TokenType.SEMICOLON
            ) ?: return null

            if (paths.any { it.type != TokenType.STRING && it.type != TokenType.COMMA }) {
                return null
            }

            return ParserResult(
                size,
                ImportStatement(
                    paths.filter { it.type != TokenType.COMMA }.map { it.value }
                )
            )
        }
    }
}