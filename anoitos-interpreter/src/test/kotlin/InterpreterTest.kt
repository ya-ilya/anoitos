import org.anoitos.interpreter.Interpreter
import org.anoitos.interpreter.context.Context
import org.anoitos.lexer.Lexer
import org.anoitos.parser.Parser
import kotlin.test.Test
import kotlin.test.assertEquals

class InterpreterTest {
    private fun interpret(text: String): Pair<Context, Any?> {
        val tokens = Lexer.lex(text)
        val statements = Parser.parse(tokens)
        val context = Context()

        return context to Interpreter.interpret(statements, context)
    }

    @Test
    fun interpret() {
        val (context, _) = interpret("var variable = 10; variable = 20;")

        assertEquals(
            20.0,
            context.getVariable("variable")
        )
    }
}