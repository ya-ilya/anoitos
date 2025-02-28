package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.ParserResult
import org.anoitos.parser.element.ParserElement
import org.anoitos.parser.extensions.SearchResult
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class IfStatement(
    val ifCondition: ParserElement,
    val ifBlock: BlockStatement,
    val elIfs: Map<ParserElement, BlockStatement>,
    val elseBlock: BlockStatement?
) : Statement {
    companion object : StatementParser<IfStatement> {
        override fun parse(input: List<Token>): ParserResult<IfStatement>? {
            var (size, _, _, condition, _, _, body, _) = input.search(
                TokenType.IF,
                TokenType.LPAREN,
                TokenType.SEARCH_GROUP,
                TokenType.RPAREN,
                TokenType.LBRACE,
                TokenType.SEARCH_GROUP,
                TokenType.RBRACE
            ) ?: return null

            val elIfs = mutableMapOf<ParserElement, BlockStatement>()
            while (true) {
                val (elIfSize, _, _, elIfCondition, _, _, elIfBody, _) = input.drop(size).search(
                    TokenType.ELIF,
                    TokenType.LPAREN,
                    TokenType.SEARCH_GROUP,
                    TokenType.RPAREN,
                    TokenType.LBRACE,
                    TokenType.SEARCH_GROUP,
                    TokenType.RBRACE
                ) ?: break

                size += elIfSize
                elIfs[Parser.parseElement(elIfCondition)!!.element] = BlockStatement.parse(elIfBody).element
            }

            val (elseSize, _, _, elseBody, _) = input.drop(size).search(
                TokenType.ELSE,
                TokenType.LBRACE,
                TokenType.SEARCH_GROUP,
                TokenType.RBRACE
            ) ?: SearchResult(0, emptyList())

            return if (elseSize != 0) {
                size += elseSize
                ParserResult(
                    size,
                    IfStatement(
                        Parser.parseElement(condition)!!.element,
                        BlockStatement.parse(body).element,
                        elIfs,
                        BlockStatement.parse(elseBody).element
                    )
                )
            } else {
                ParserResult(
                    size,
                    IfStatement(
                        Parser.parseElement(condition)!!.element,
                        BlockStatement.parse(body).element,
                        elIfs,
                        null
                    )
                )
            }
        }
    }
}