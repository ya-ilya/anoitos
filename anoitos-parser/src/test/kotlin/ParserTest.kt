import org.anoitos.lexer.Lexer
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import org.anoitos.parser.Parser
import org.anoitos.parser.element.elements.TokenElement
import org.anoitos.parser.expression.expressions.BooleanExpression
import org.anoitos.parser.expression.expressions.CallExpression
import org.anoitos.parser.expression.expressions.NumberExpression
import org.anoitos.parser.expression.expressions.PathExpression
import org.anoitos.parser.statement.statements.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserTest {
    @Test
    fun parseBreak() {
        assertEquals(
            BreakStatement(),
            Parser.parse(Lexer.lex("break;"))[0]
        )
    }

    @Test
    fun parseClass() {
        assertEquals(
            ClassStatement(
                Token(TokenType.ID, "myClass"),
                listOf(
                    VarStatement(
                        Token(TokenType.ID, "variable"),
                        NumberExpression(
                            listOf(
                                TokenElement(
                                    Token(TokenType.NUMBER, "10")
                                )
                            )
                        )
                    )
                ),
                listOf(
                    FunStatement(
                        Token(TokenType.ID, "myFunction"),
                        listOf("param"),
                        BlockStatement(
                            listOf(
                                ReturnStatement(
                                    NumberExpression(
                                        listOf(
                                            TokenElement(
                                                Token(TokenType.NUMBER, "42")
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ),
            Parser.parse(Lexer.lex("class myClass { var variable = 10; fun myFunction(param) { return 42; } }"))[0]
        )
    }

    @Test
    fun parseContinue() {
        assertEquals(
            ContinueStatement(),
            Parser.parse(Lexer.lex("continue;"))[0]
        )
    }

    @Test
    fun parseDo() {
        assertEquals(
            DoStatement(
                BooleanExpression(
                    listOf(
                        TokenElement(Token(TokenType.TRUE, "true"))
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
    fun parseForEach() {
        assertEquals(
            ForEachStatement(
                Token(TokenType.ID, "item"),
                PathExpression(
                    listOf(
                        TokenElement(
                            Token(TokenType.ID, "items")
                        )
                    )
                ),
                BlockStatement(
                    listOf(
                        ContinueStatement()
                    )
                )
            ),
            Parser.parse(Lexer.lex("for (item in items) { continue; }"))[0]
        )
    }

    @Test
    fun parseFor() {
        assertEquals(
            ForStatement(
                Token(TokenType.ID, "index"),
                NumberExpression(
                    listOf(
                        TokenElement(Token(TokenType.NUMBER, "0"))
                    )
                ),
                NumberExpression(
                    listOf(
                        TokenElement(Token(TokenType.NUMBER, "10"))
                    )
                ),
                BlockStatement(
                    listOf(
                        CallExpression(
                            Token(TokenType.ID, "println"),
                            listOf(
                                PathExpression(
                                    listOf(
                                        TokenElement(Token(TokenType.ID, "index"))
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
    fun parseFun() {
        assertEquals(
            FunStatement(
                Token(TokenType.ID, "myFunction"),
                listOf("param"),
                BlockStatement(
                    listOf(
                        ReturnStatement(
                            NumberExpression(
                                listOf(
                                    TokenElement(
                                        Token(TokenType.NUMBER, "42")
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
    fun parseIf() {
        assertEquals(
            IfStatement(
                BooleanExpression(
                    listOf(
                        TokenElement(Token(TokenType.TRUE, "true"))
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
    fun parseImport() {
        assertEquals(
            ImportStatement(listOf("io", "math", "logic")),
            Parser.parse(Lexer.lex("import 'io', 'math', 'logic';"))[0]
        )
    }

    @Test
    fun parseNew() {
        assertEquals(
            NewStatement(
                Token(TokenType.ID, "myClass"),
                emptyList()
            ),
            Parser.parse(Lexer.lex("new myClass();"))[0]
        )
    }

    @Test
    fun parseSet() {
        assertEquals(
            SetStatement(
                PathExpression(
                    listOf(
                        TokenElement(Token(TokenType.ID, "variable"))
                    )
                ),
                NumberExpression(
                    listOf(
                        TokenElement(
                            Token(TokenType.NUMBER, "42")
                        )
                    )
                )
            ),
            Parser.parse(Lexer.lex("variable = 42;"))[0]
        )
    }

    @Test
    fun parseVar() {
        assertEquals(
            VarStatement(
                Token(TokenType.ID, "variable"),
                NumberExpression(
                    listOf(
                        TokenElement(
                            Token(TokenType.NUMBER, "10")
                        )
                    )
                )
            ),
            Parser.parse(Lexer.lex("var variable = 10;"))[0] as VarStatement
        )
    }

    @Test
    fun parseWhile() {
        assertEquals(
            WhileStatement(
                BooleanExpression(
                    listOf(
                        TokenElement(Token(TokenType.TRUE, "true"))
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
}