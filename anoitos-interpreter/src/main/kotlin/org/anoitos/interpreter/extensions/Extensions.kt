package org.anoitos.interpreter.extensions

import org.anoitos.interpreter.Interpreter
import org.anoitos.interpreter.context.Context
import org.anoitos.parser.expression.Expression
import org.anoitos.parser.statement.Statement

fun Statement.interpret(context: Context): Any? {
    return Interpreter.interpret(this, context)
}

fun Expression.interpret(context: Context): Any? {
    return Interpreter.interpret(this, context)
}