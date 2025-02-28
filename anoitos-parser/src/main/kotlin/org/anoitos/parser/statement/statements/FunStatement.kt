package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.ParserResult
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class FunStatement(
    val name: Token,
    val parameters: List<String>,
    val body: BlockStatement
) : Statement {
    companion object : StatementParser<FunStatement> {
        override fun parse(input: List<Token>): ParserResult<FunStatement>? {
            val (size, _, name, _, parameters, _, _, body, _) = input.search(
                TokenType.FUN,
                TokenType.ID,
                TokenType.LPAREN,
                TokenType.SEARCH_GROUP,
                TokenType.RPAREN,
                TokenType.LBRACE,
                TokenType.SEARCH_GROUP,
                TokenType.RBRACE
            ) ?: return null

            val parameterNames = mutableListOf<String>()

            for (index in 0..parameters.lastIndex step 2) {
                parameterNames.add(parameters[index].value)
            }

            return ParserResult(
                size,
                FunStatement(
                    name[0],
                    parameterNames,
                    BlockStatement.parse(body).element
                )
            )
        }
    }
}