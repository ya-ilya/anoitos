package org.anoitos.interpreter.statement

import org.anoitos.interpreter.statement.interpreters.*
import org.anoitos.parser.statement.statements.*

object StatementInterpreterRegistry {
    val interpreters = mapOf(
        BlockStatement::class to BlockInterpreter,
        BreakStatement::class to BreakInterpreter,
        CallStatement::class to CallInterpreter,
        ClassStatement::class to ClassInterpreter,
        ContinueStatement::class to ContinueInterpreter,
        DoStatement::class to DoInterpreter,
        EmptyStatement::class to EmptyInterpreter,
        ExpressionStatement::class to ExpressionInterpreter,
        ForEachStatement::class to ForEachInterpreter,
        ForStatement::class to ForInterpreter,
        FunStatement::class to FunInterpreter,
        IfStatement::class to IfInterpreter,
        ImportStatement::class to ImportInterpreter,
        ReturnStatement::class to ReturnInterpreter,
        SetStatement::class to SetInterpreter,
        TokenStatement::class to TokenInterpreter,
        VarStatement::class to VarInterpreter,
        WhileStatement::class to WhileInterpreter
    )
}