package org.anoitos.parser.statement

import org.anoitos.parser.statement.statements.*

object StatementRegistry {
    val statements = arrayOf(
        VarStatement,
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
        SetStatement
    )
}