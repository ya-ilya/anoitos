package org.anoitos.parser.expression.expressions

import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.expression.ExpressionParser
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.statements.CallStatement
import org.anoitos.parser.statement.statements.TokenStatement

data class PathExpression(
    val statements: List<Statement>
) : Expression {
    companion object : ExpressionParser<PathExpression> {
        fun parse(input: List<Token>, withCallStatements: Boolean): Pair<Int, PathExpression>? {
            val statements = mutableListOf<Statement>()
            var size = 0

            do {
                var found = false
                val callStatement = CallStatement.parse(input.drop(size))

                if (callStatement != null && withCallStatements) {
                    statements.add(callStatement.second)
                    size += callStatement.first
                    found = true
                } else {
                    val tokenStatement = TokenStatement(input[size])

                    if (tokenStatement.token.type == TokenType.ID) {
                        statements.add(tokenStatement)
                        size += 1
                        found = true
                    }
                }

                if (!found) {
                    return null
                }

                if (input.drop(size).getOrNull(0)?.type == TokenType.DOT) {
                    size++
                } else {
                    break
                }
            } while (true)

            return size to PathExpression(statements)
        }

        override fun parse(input: List<Token>): Pair<Int, PathExpression>? {
            return parse(input, true)
        }
    }
}