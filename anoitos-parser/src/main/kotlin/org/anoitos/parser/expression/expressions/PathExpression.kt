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
        override fun parse(input: List<Token>): Pair<Int, PathExpression>? {
            val statements = mutableListOf<Statement>()
            var size = 0

            while (size < input.size) {
                var found = false

                val callStatement = CallStatement.parse(input.drop(size))
                if (callStatement != null) {
                    statements.add(callStatement.second)
                    size += callStatement.first
                    found = true
                }

                if (!found && input[size].type == TokenType.ID) {
                    statements.add(TokenStatement(input[size]))
                    size++
                    found = true
                }

                if (!found) {
                    return null
                }

                if (size < input.size && input[size].type == TokenType.DOT) {
                    size++
                } else {
                    break
                }
            }

            return if (!statements.any { it is TokenStatement }) {
                null
            } else {
                size to PathExpression(statements)
            }
        }
    }
}