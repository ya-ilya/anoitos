package org.anoitos.parser.statement

import org.anoitos.lexer.token.Token
import org.anoitos.parser.ParserResult

interface StatementParser<T : Statement> {
    fun parse(input: List<Token>): ParserResult<T>?
}