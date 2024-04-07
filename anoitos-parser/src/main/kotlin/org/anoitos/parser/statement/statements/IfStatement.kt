package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class IfStatement(
    val ifCondition: Statement,
    val ifBlock: BlockStatement,
    val elIfs: Map<Statement, BlockStatement>,
    val elseBlock: BlockStatement?
) : Statement {
    companion object : StatementParser<IfStatement> {
        override fun parse(input: List<Token>): Pair<Int, IfStatement>? {
            var (size, result) = input.search(
                TokenType.IF,
                TokenType.LPAREN,
                TokenType.SEARCH_GROUP,
                TokenType.RPAREN,
                TokenType.LBRACE,
                TokenType.SEARCH_GROUP,
                TokenType.RBRACE
            ) ?: return null
            val (_, _, condition, _, _, body, _) = result

            val elIfs = mutableMapOf<Statement, BlockStatement>()
            while (true) {
                val (elIfSize, elIfResult) = input.drop(size).search(
                    TokenType.ELIF,
                    TokenType.LPAREN,
                    TokenType.SEARCH_GROUP,
                    TokenType.RPAREN,
                    TokenType.LBRACE,
                    TokenType.SEARCH_GROUP,
                    TokenType.RBRACE
                ) ?: break
                val (_, _, elIfCondition, _, _, elIfBody, _) = elIfResult

                size += elIfSize
                elIfs[Parser.parseStatement(elIfCondition).second] = BlockStatement.parse(elIfBody).second
            }

            val (elseSize, elseResult) = input.drop(size).search(
                TokenType.ELSE,
                TokenType.LBRACE,
                TokenType.SEARCH_GROUP,
                TokenType.RBRACE
            ) ?: Pair(null, null)

            return if (elseSize != null && elseResult != null) {
                val (_, _, elseBody, _) = elseResult

                size += elseSize
                size to IfStatement(
                    Parser.parseStatement(condition).second,
                    BlockStatement.parse(body).second,
                    elIfs,
                    BlockStatement.parse(elseBody).second
                )
            } else {
                size to IfStatement(
                    Parser.parseStatement(condition).second,
                    BlockStatement.parse(body).second,
                    elIfs,
                    null
                )
            }
        }
    }
}