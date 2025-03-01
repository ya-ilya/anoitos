package org.anoitos.lexer

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType

object Lexer {
    private val quotes = listOf('\'', '"')

    fun lex(input: String): List<Token> {
        var current = 0
        val tokens = mutableListOf<Token>()

        while (current < input.length) {
            lexToken(current, input).also { result ->
                if (result == null) {
                    current += 1
                } else {
                    current += result.size
                    tokens.add(result.token)
                }
            }
        }

        return tokens
    }

    private fun lexToken(current: Int, input: String): LexerResult? {
        if (input[current].isWhitespace()) {
            return null
        }

        for (keyword in TokenType.keywords) {
            val substring = input.substring(current, input.length)
            if (substring.startsWith("${keyword.value} ") || substring.startsWith("${keyword.value};")) {
                return LexerResult(
                    keyword.value.length,
                    Token(keyword, keyword.value)
                )
            }
        }

        for (other in TokenType.logicals + TokenType.numerics + TokenType.other) {
            val substring = input.substring(current, input.length)
            if (substring.startsWith(other.value)) {
                return LexerResult(
                    other.value.length,
                    Token(other, other.value)
                )
            }
        }

        if (quotes.contains(input[current])) {
            val builder = StringBuilder()

            for (char in input.drop(current + 1)) {
                if (quotes.contains(char)) {
                    break
                }

                builder.append(char)
            }

            return LexerResult(
                builder.length + 2,
                Token(TokenType.STRING, builder.toString())
            )
        }

        if (input[current].isDigit()) {
            val builder = StringBuilder()

            for ((index, char) in input.drop(current).withIndex()) {
                builder.append(
                    when {
                        char.isDigit() -> char
                        char == TokenType.DOT.value[0] && input.elementAtOrNull(current + index + 1)
                            ?.isDigit() == true -> {
                            if (builder.contains(TokenType.DOT.value[0])) {
                                break
                            }

                            char
                        }

                        else -> break
                    }
                )
            }

            return LexerResult(
                builder.length,
                Token(TokenType.NUMBER, builder.toString())
            )
        }

        if (input[current].isLetter()) {
            val builder = StringBuilder()

            for (char in input.drop(current)) {
                if (char.isLetterOrDigit()) {
                    builder.append(char)
                } else {
                    break
                }
            }

            return LexerResult(
                builder.length,
                Token(TokenType.ID, builder.toString())
            )
        }

        throw IllegalStateException("Unknown token ${input[current]} at position $current in $input")
    }
}