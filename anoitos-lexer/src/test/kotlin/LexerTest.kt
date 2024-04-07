import org.anoitos.lexer.Lexer
import org.anoitos.lexer.token.Token
import org.anoitos.lexer.token.TokenType
import kotlin.test.Test
import kotlin.test.assertContentEquals

class LexerTest {
    @Test
    fun lexVariable() {
        assertContentEquals(
            Lexer.lex("var intVariable = 10;"),
            listOf(
                Token(TokenType.VAR, "var"),
                Token(TokenType.ID, "intVariable"),
                Token(TokenType.EQUAL, "="),
                Token(TokenType.INT, "10"),
                Token(TokenType.SEMICOLON, ";")
            )
        )

        assertContentEquals(
            Lexer.lex("var stringVariable = 'Simple string';"),
            listOf(
                Token(TokenType.VAR, "var"),
                Token(TokenType.ID, "stringVariable"),
                Token(TokenType.EQUAL, "="),
                Token(TokenType.STRING, "Simple string"),
                Token(TokenType.SEMICOLON, ";")
            )
        )
    }

    @Test
    fun lexFun() {
        assertContentEquals(
            Lexer.lex("fun function(parameter) { }"),
            listOf(
                Token(TokenType.FUN, "fun"),
                Token(TokenType.ID, "function"),
                Token(TokenType.LPAREN, "("),
                Token(TokenType.ID, "parameter"),
                Token(TokenType.RPAREN, ")"),
                Token(TokenType.LBRACE, "{"),
                Token(TokenType.RBRACE, "}")
            )
        )
    }
}