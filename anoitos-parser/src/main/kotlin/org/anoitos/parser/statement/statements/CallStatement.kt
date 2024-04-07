package org.anoitos.parser.statement.statements

import org.anoitos.interpreter.context.Context
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.extensions.search
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.StatementParser

@Suppress("unused")
data class CallStatement(
    val name: Token,
    val arguments: List<Statement>
) : Statement {
    companion object : StatementParser<CallStatement> {
        override fun parse(input: List<Token>): Pair<Int, CallStatement>? {
            val (size, name, _, arguments, _) = input.search(
                TokenType.ID,
                TokenType.LPAREN,
                TokenType.SEARCH_GROUP,
                TokenType.RPAREN
            ) ?: return null

            val argumentStatements = mutableListOf<Statement>()
            val argument = mutableListOf<Token>()
            var parensAndBrackets = 0

            for (token in arguments) {
                if (token.type == TokenType.LPAREN || token.type == TokenType.LBRACKET) {
                    parensAndBrackets++
                } else if (token.type == TokenType.RPAREN || token.type == TokenType.RBRACKET) {
                    parensAndBrackets--
                }

                if (token.type == TokenType.COMMA && parensAndBrackets == 0) {
                    argumentStatements.add(Parser.parseStatement(argument).second)
                    argument.clear()
                } else {
                    argument.add(token)
                }
            }

            if (argument.isNotEmpty()) {
                argumentStatements.add(Parser.parseStatement(argument).second)
            }

            return size to CallStatement(
                name[0],
                argumentStatements
            )
        }
    }

    override fun interpret(context: Context): Any? {
        return context.executeFunction(
            name.value,
            arguments.map { it.interpret(context)!! }
        )
    }
}