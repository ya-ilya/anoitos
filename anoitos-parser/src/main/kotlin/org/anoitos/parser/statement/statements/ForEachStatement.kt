package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.ParserResult
import org.anoitos.parser.element.ParserElement
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class ForEachStatement(
    val identifier: Token,
    val value: ParserElement,
    val body: BlockStatement
) : Statement {
    companion object : StatementParser<ForEachStatement> {
        override fun parse(input: List<Token>): ParserResult<ForEachStatement>? {
            val (size, _, _, identifier, _, value, _, _, body, _) = input.search(
                TokenType.FOR,
                TokenType.LPAREN,
                TokenType.ID,
                TokenType.IN,
                TokenType.SEARCH_GROUP,
                TokenType.RPAREN,
                TokenType.LBRACE,
                TokenType.SEARCH_GROUP,
                TokenType.RBRACE
            ) ?: return null

            return ParserResult(
                size,
                ForEachStatement(
                    identifier[0],
                    Parser.parseElement(value)!!.element,
                    BlockStatement.parse(body).element
                )
            )
        }
    }
}