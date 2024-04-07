package org.anoitos.interpreter.statement.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.statement.StatementInterpreter
import org.anoitos.parser.statement.statements.ImportStatement

object ImportInterpreter : StatementInterpreter<ImportStatement> {
    override fun interpret(statement: ImportStatement, context: Context): Any? {
        for (path in statement.paths) {
            context.addImport(path)
        }

        return null
    }
}