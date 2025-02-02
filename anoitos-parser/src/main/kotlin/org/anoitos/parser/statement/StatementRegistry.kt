package org.anoitos.parser.statement

import org.anoitos.parser.statement.statements.*

object StatementRegistry {
    val statements = arrayOf(
        VarStatement,
        SetStatement,
        ExpressionStatement,
        FunStatement,
        ClassStatement,
        ReturnStatement,
        IfStatement,
        ForStatement,
        ForEachStatement,
        WhileStatement,
        DoStatement,
        BreakStatement,
        ContinueStatement,
        ImportStatement,
        NewStatement,
        CallStatement
    )
}