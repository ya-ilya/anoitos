package org.anoitos.parser.statement

import org.anoitos.lexer.token.Token

interface StatementParser<T : Statement> {
    fun parse(input: List<Token>): Pair<Int, T>
}