package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.ParserResult
import org.anoitos.parser.element.ParserElement
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class NewStatement(
    val name: Token,
    val arguments: List<ParserElement>
) : Statement {
    companion object : StatementParser<NewStatement> {
        override fun parse(input: List<Token>): ParserResult<NewStatement>? {
            val (size, _, name, _, arguments, _) = input.search(
                TokenType.NEW,
                TokenType.ID,
                TokenType.LPAREN,
                TokenType.SEARCH_GROUP,
                TokenType.RPAREN
            ) ?: return null

            val argumentElements = mutableListOf<ParserElement>()
            val argument = mutableListOf<Token>()
            var parens = 0
            var brackets = 0
            var braces = 0

            for (token in arguments) {
                when (token.type) {
                    TokenType.LPAREN -> parens++
                    TokenType.LBRACKET -> brackets++
                    TokenType.LBRACE -> braces++
                    TokenType.RPAREN -> parens--
                    TokenType.RBRACKET -> brackets--
                    TokenType.RBRACE -> braces--
                    else -> {}
                }

                if (token.type == TokenType.COMMA && parens == 0 && brackets == 0 && braces == 0) {
                    argumentElements.add(Parser.parseElement(argument)!!.element)
                    argument.clear()
                } else {
                    argument.add(token)
                }
            }

            if (argument.isNotEmpty()) {
                argumentElements.add(Parser.parseElement(argument)!!.element)
            }

            return ParserResult(
                size,
                NewStatement(
                    name[0],
                    argumentElements
                )
            )
        }
    }
}