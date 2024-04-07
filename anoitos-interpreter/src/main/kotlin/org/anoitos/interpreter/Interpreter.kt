package org.anoitos.interpreter

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.expression.ExpressionInterpreterRegistry
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.result.InterpretResult
import org.anoitos.interpreter.statement.StatementInterpreterRegistry
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.statement.Statement
import org.anoitos.parser.statement.statements.BreakStatement
import org.anoitos.parser.statement.statements.ContinueStatement

object Interpreter {
    fun interpret(statements: List<Statement>, context: Context): Any? {
        for (statement in statements) {
            when (statement) {
                is BreakStatement -> return InterpretResult.Break
                is ContinueStatement -> return InterpretResult.Continue
            }

            val result = statement.interpret(context)

            if (result is InterpretResult.Return) {
                return result
            }
        }

        return null
    }

    fun interpret(statement: Statement, context: Context): Any? {
        val interpreter = StatementInterpreterRegistry.interpreters[statement::class]!!
        val interpret = interpreter.javaClass.getDeclaredMethod(
            "interpret",
            statement.javaClass,
            Context::class.java
        )

        return interpret(interpreter, statement, context)
    }

    fun interpret(expression: Expression, context: Context): Any? {
        val interpreter = ExpressionInterpreterRegistry.interpreters[expression::class]!!
        val interpret = interpreter.javaClass.getDeclaredMethod(
            "interpret",
            expression.javaClass,
            Context::class.java
        )

        return interpret(interpreter, expression, context)
    }
}