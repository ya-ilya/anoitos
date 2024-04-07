import org.anoitos.interpreter.Interpreter
import org.anoitos.interpreter.context.Context
import org.anoitos.lexer.Lexer
import org.anoitos.parser.Parser
import kotlin.test.Test
import kotlin.test.assertEquals

class InterpreterTest {
    @Test
    fun interpret() {
        val tokens = Lexer.lex("var variable = 10; variable = 20;")
        val statements = Parser.parse(tokens)
        val context = Context()

        Interpreter.interpret(statements, context)

        assertEquals(
            20,
            context.getVariable("variable")
        )
    }
}