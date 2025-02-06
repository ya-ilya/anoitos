package org.anoitos.interpreter.statement

import org.anoitos.interpreter.statement.interpreters.*
import org.anoitos.parser.statement.statements.*

object StatementInterpreterRegistry {
    val interpreters = mapOf(
        BlockStatement::class to BlockInterpreter,
        BreakStatement::class to BreakInterpreter,
        ClassStatement::class to ClassInterpreter,
        ContinueStatement::class to ContinueInterpreter,
        DoStatement::class to DoInterpreter,
        ForEachStatement::class to ForEachInterpreter,
        ForStatement::class to ForInterpreter,
        FunStatement::class to FunInterpreter,
        IfStatement::class to IfInterpreter,
        ImportStatement::class to ImportInterpreter,
        NewStatement::class to NewInterpreter,
        ReturnStatement::class to ReturnInterpreter,
        SetStatement::class to SetInterpreter,
        VarStatement::class to VarInterpreter,
        WhileStatement::class to WhileInterpreter
    )
}