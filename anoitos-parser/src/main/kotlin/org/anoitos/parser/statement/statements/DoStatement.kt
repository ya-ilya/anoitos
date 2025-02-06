package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.element.ParserElement
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class DoStatement(val condition: ParserElement, val body: BlockStatement) : Statement {
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
                Parser.parseElement(condition)!!.second,
                BlockStatement.parse(body).second
            )
        }
    }
}