import org.anoitos.lexer.Lexer
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.expression.expressions.BooleanExpression
import org.anoitos.parser.expression.expressions.NumberExpression
import org.anoitos.parser.expression.expressions.PathExpression
import org.anoitos.parser.statement.statements.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserTest {
    @Test
    fun parseVar() {
        assertEquals(
            VarStatement(
                Token(TokenType.ID, "variable"),
                ExpressionStatement(
                    NumberExpression(
                        listOf(
                            TokenStatement(
                                Token(TokenType.NUMBER, "10")
                            )
                        )
                    )
                )
            ),
            Parser.parse(Lexer.lex("var variable = 10;"))[0] as VarStatement
        )
    }

    @Test
    fun parseFun() {
        assertEquals(
            FunStatement(
                Token(TokenType.ID, "myFunction"),
                listOf("param"),
                BlockStatement(
                    listOf(
                        ReturnStatement(
                            ExpressionStatement(
                                NumberExpression(
                                    listOf(
                                        TokenStatement(
                                            Token(TokenType.NUMBER, "42")
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ),
            Parser.parse(Lexer.lex("fun myFunction(param) { return 42; }"))[0]
        )
    }

    @Test
    fun parseCall() {
        assertEquals(
            CallStatement(
                Token(TokenType.ID, "myFunction"),
                listOf(
                    ExpressionStatement(
                        PathExpression(
                            listOf(
                                TokenStatement(
                                    Token(TokenType.ID, "param")
                                )
                            )
                        )
                    )
                )
            ),
            Parser.parse(Lexer.lex("myFunction(param)"))[0]
        )
    }

    @Test
    fun parseIf() {
        assertEquals(
            IfStatement(
                ExpressionStatement(
                    BooleanExpression(
                        listOf(
                            TokenStatement(Token(TokenType.TRUE, "true"))
                        )
                    )
                ),
                BlockStatement(
                    emptyList()
                ),
                emptyMap(),
                BlockStatement(
                    emptyList()
                )
            ),
            Parser.parse(Lexer.lex("if (true) {} else {}"))[0]
        )
    }

    @Test
    fun parseWhile() {
        assertEquals(
            WhileStatement(
                ExpressionStatement(
                    BooleanExpression(
                        listOf(
                            TokenStatement(Token(TokenType.TRUE, "true"))
                        )
                    )
                ),
                BlockStatement(
                    listOf(
                        ContinueStatement()
                    )
                )
            ),
            Parser.parse(Lexer.lex("while (true) { continue; }"))[0]
        )
    }

    @Test
    fun parseDo() {
        assertEquals(
            DoStatement(
                ExpressionStatement(
                    BooleanExpression(
                        listOf(
                            TokenStatement(Token(TokenType.TRUE, "true"))
                        )
                    )
                ),
                BlockStatement(
                    listOf(
                        ContinueStatement()
                    )
                )
            ),
            Parser.parse(Lexer.lex("do { continue; } while (true)"))[0]
        )
    }

    @Test
    fun parseFor() {
        assertEquals(
            ForStatement(
                Token(TokenType.ID, "index"),
                NumberExpression(
                    listOf(
                        TokenStatement(Token(TokenType.NUMBER, "0"))
                    )
                ),
                NumberExpression(
                    listOf(
                        TokenStatement(Token(TokenType.NUMBER, "10"))
                    )
                ),
                BlockStatement(
                    listOf(
                        CallStatement(
                            Token(TokenType.ID, "println"),
                            listOf(
                                ExpressionStatement(
                                    PathExpression(
                                        listOf(
                                            TokenStatement(Token(TokenType.ID, "index"))
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ),
            Parser.parse(Lexer.lex("for (index in 0..10) { println(index); }"))[0]
        )
    }

    @Test
    fun parseSet() {
        assertEquals(
            SetStatement(
                PathExpression(
                    listOf(
                        TokenStatement(Token(TokenType.ID, "variable"))
                    )
                ),
                ExpressionStatement(
                    NumberExpression(
                        listOf(
                            TokenStatement(
                                Token(TokenType.NUMBER, "42")
                            )
                        )
                    )
                )
            ),
            Parser.parse(Lexer.lex("variable = 42;"))[0]
        )
    }
}