package org.anoitos.lexer

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType

object Lexer {
    fun lex(input: String): List<Token> {
        var current = 0
        val tokens = mutableListOf<Token>()

        while (current < input.length) {
            lexToken(current, input).also { (newCurrent, token) ->
                current = newCurrent

                when (token.type) {
                    TokenType.EMPTY -> {}

                    else -> tokens.add(token)
                }
            }
        }

        return tokens
    }

    private fun lexToken(current: Int, input: String): Pair<Int, Token> {
        if (input[current].isWhitespace()) {
            return (current + 1 to Token(TokenType.EMPTY, " "))
        }

        for (keyword in TokenType.keywords) {
            val substring = input.substring(current, input.length)
            if (substring.startsWith("${keyword.value} ") || substring.startsWith("${keyword.value};")) {
                return (current + keyword.value.length to Token(keyword, keyword.value))
            }
        }

        for (other in TokenType.other) {
            val substring = input.substring(current, input.length)
            if (substring.startsWith(other.value)) {
                return (current + other.value.length to Token(other, other.value))
            }
        }

        if (input[current] == '"' || input[current] == '\'') {
            var string = ""

            for (char in input.drop(current + 1)) {
                if (char == '"' || char == '\'') {
                    break
                } else {
                    string += char
                }
            }

            return (current + string.length + 2 to Token(TokenType.STRING, string))
        }

        if (input[current].isDigit()) {
            var number = ""
            var type = TokenType.INT

            for ((index, char) in input.drop(current).withIndex()) {
                number += if (char.isDigit()) {
                    char
                } else if (char == '.' && input.elementAtOrNull(index + 1)?.isDigit() == true) {
                    if (number.contains('.')) {
                        break
                    }

                    type = TokenType.DOUBLE
                    char
                } else {
                    break
                }
            }

            return (current + number.length to Token(type, number))
        }

        val inputFromCurrent = input.drop(current)

        if (input[current].isLetter()) {
            var identifier = ""

            for (char in inputFromCurrent) {
                if (char.isLetterOrDigit()) {
                    identifier += char
                } else {
                    break
                }
            }

            return (current + identifier.length to Token(TokenType.ID, identifier))
        }

        throw Exception("Unknown token ${input[current]} at position $current in $input")
    }
}