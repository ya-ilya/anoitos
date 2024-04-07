package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.Interpreter
import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.BlockStatement

object BlockInterpreter : StatementInterpreter<BlockStatement> {
    override fun interpret(statement: BlockStatement, context: Context): Any? {
        return Interpreter.interpret(statement.statements, context)
    }
}