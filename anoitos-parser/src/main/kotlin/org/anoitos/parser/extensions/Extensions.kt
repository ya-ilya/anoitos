package org.anoitos.parser.extensions

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType

fun List<Token>.search(vararg types: TokenType): Pair<Int, ExtendedComponentsList<List<Token>>>? {
    if (types.filter { it != TokenType.SEARCH_GROUP }.size > this.size) {
        return null
    }

    var size = 0
    var inGroup = false
    var typeIndex = 0
    var tokenIndex = 0
    val group = mutableListOf<Token>()
    val result = mutableListOf<List<Token>>()
    var bracesParensAndBrackets = 0

    while (typeIndex < types.size && tokenIndex < this.size) {
        val token = this[tokenIndex]
        val type = types[typeIndex]

        if (inGroup) {
            when (token.type) {
                TokenType.LBRACE, TokenType.LPAREN, TokenType.LBRACKET -> {
                    bracesParensAndBrackets++
                }

                TokenType.RBRACE, TokenType.RPAREN, TokenType.RBRACKET -> {
                    bracesParensAndBrackets--
                }

                else -> {}
            }

            group.add(token)

            if (this.elementAtOrNull(tokenIndex + 1)?.type == types.elementAtOrNull(typeIndex + 1) && bracesParensAndBrackets == 0) {
                inGroup = false
                typeIndex++
                bracesParensAndBrackets = 0
                result.add(group.toList().also { size += it.size })
                group.clear()
            }
        } else {
            when {
                type == TokenType.SEARCH_GROUP -> {
                    if (token.type == types.elementAtOrNull(typeIndex + 1)) {
                        result.add(listOf())
                        result.add(listOf(token))
                        size++
                        tokenIndex++
                    } else {
                        inGroup = true
                    }

                    continue
                }

                type != token.type -> {
                    return null
                }

                else -> {
                    result.add(listOf(token))
                    size++
                }
            }

            typeIndex++
        }

        tokenIndex++
    }

    return size to ExtendedComponentsList(result)
}

class ExtendedComponentsList<T>(collection: Collection<T>) : ArrayList<T>(collection) {
    operator fun component6() = this[5]
    operator fun component7() = this[6]
    operator fun component8() = this[7]
    operator fun component9() = this[8]
    operator fun component10() = this[9]
    operator fun component11() = this[10]
}