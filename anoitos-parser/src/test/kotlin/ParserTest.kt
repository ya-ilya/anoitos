import org.anoitos.lexer.Lexer
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.expression.expressions.NumberExpression
import org.anoitos.parser.statement.statements.ExpressionStatement
import org.anoitos.parser.statement.statements.TokenStatement
import org.anoitos.parser.statement.statements.VarStatement
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserTest {
    @Test
    fun parseVariable() {
        assertEquals(
            VarStatement(
                Token(TokenType.ID, "variable"),
                ExpressionStatement(
                    NumberExpression(
                        listOf(
                            TokenStatement(
                                Token(TokenType.INT, "10")
                            )
                        )
                    )
                )
            ),
            Parser.parse(Lexer.lex("var variable = 10;"))[0] as VarStatement
        )
    }
}