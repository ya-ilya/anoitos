package org.anoitos.parser.expression

import org.anoitos.lexer.token.Token
import org.anoitos.parser.ParserResult

interface ExpressionParser<T : Expression> {
    fun parse(input: List<Token>): ParserResult<T>?
}