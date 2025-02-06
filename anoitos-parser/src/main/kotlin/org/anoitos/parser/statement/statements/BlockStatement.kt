package org.anoitos.parser.statement.statements

import org.anoitos.lexer.token.Token
import org.anoitos.parser.Parser
import org.anoitos.parser.element.ParserElement
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

data class BlockStatement(val elements: List<ParserElement>) : Statement {
    companion object : StatementParser<BlockStatement> {
        override fun parse(input: List<Token>): Pair<Int, BlockStatement> {
            return input.size to BlockStatement(Parser.parse(input))
        }
    }
}