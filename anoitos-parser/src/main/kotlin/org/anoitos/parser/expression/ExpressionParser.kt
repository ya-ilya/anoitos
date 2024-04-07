package org.anoitos.parser.expression

import org.anoitos.lexer.token.Token

interface ExpressionParser<T : Expression> {
    fun parse(input: List<Token>): Pair<Int, T>
}