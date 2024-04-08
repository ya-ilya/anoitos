package org.anoitos.parser.statement

import org.anoitos.parser.statement.statements.*

object StatementRegistry {
    val statements = arrayOf(
        VarStatement,
        FunStatement,
        CallStatement,
        ClassStatement,
        ReturnStatement,
        ImportStatement,
        IfStatement,
        ForStatement,
        ForEachStatement,
        WhileStatement,
        DoStatement,
        SetStatement,
        ExpressionStatement,
        BreakStatement,
        ContinueStatement
    )
}