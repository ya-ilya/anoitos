package org.anoitos.parser

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementRegistry
import org.anoitos.parser.statement.statements.EmptyStatement

object Parser {
    fun parse(input: List<Token>): List<Statement> {
        var current = 0
        val statements = mutableListOf<Statement>()

        while (current < input.size) {
            parseStatement(input.drop(current)).also { (size, statement) ->
                current += size

                if (statement !is EmptyStatement) {
                    statements.add(statement)
                }
            }
        }

        return statements
    }

    fun parseStatement(input: List<Token>): Pair<Int, Statement> {
        if (input[0].type == TokenType.SEMICOLON) {
            return 1 to EmptyStatement()
        }

        for (statement in StatementRegistry.statements) {
            val result = statement.parse(input)

            if (result != null) {
                return result
            }
        }

        throw IllegalStateException("No valid statement found for the given input")
    }
}