package org.anoitos.lexer

import org.anoitos.lexer.token.Token

data class LexerResult(
    val size: Int,
    val token: Token
)