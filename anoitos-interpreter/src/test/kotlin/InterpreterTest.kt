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
    fun interpretVar() {
        val (context, _) = interpret("var variable = 10; variable = 20;")

        assertEquals(
            20.0,
            context.getVariable("variable")
        )
    }

    @Test
    fun interpretFactorial() {
        val (context, _) = interpret("""
            import 'logic';

            fun factorial(n) {
                if (equals(n, 0) || equals(n, 1)) {
                    return 1;
                } else {
                    return n * factorial(n - 1);
                }
            }
            
            var result = factorial(5);
        """.trimIndent())

        assertEquals(
            120.0,
            context.getVariable("result")
        )
    }
}