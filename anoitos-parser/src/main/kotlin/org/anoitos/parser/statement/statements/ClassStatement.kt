package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class ClassStatement(
    val name: Token,
    val variables: List<VarStatement>,
    val functions: List<FunStatement>
) : Statement {
    companion object : StatementParser<ClassStatement> {
        override fun parse(input: List<Token>): Pair<Int, ClassStatement>? {
            val (size, _, name, _, body, _) = input.search(
                TokenType.CLASS,
                TokenType.ID,
                TokenType.LBRACE,
                TokenType.SEARCH_GROUP,
                TokenType.RBRACE
            ) ?: return null

            val bodyElements = Parser.parse(body)

            if (bodyElements.any { it !is VarStatement && it !is FunStatement }) {
                throw IllegalStateException("BodyStatement can contain only VarSarStatement and FunStatement")
            }

            return size to ClassStatement(
                name[0],
                bodyElements.filterIsInstance<VarStatement>(),
                bodyElements.filterIsInstance<FunStatement>()
            )
        }
    }
}