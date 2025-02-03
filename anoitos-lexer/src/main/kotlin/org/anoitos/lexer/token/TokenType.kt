package org.anoitos.lexer.token

@Suppress("SpellCheckingInspection")
enum class TokenType(
    val value: String = "",
    val group: TokenGroup = TokenGroup.OTHER
) {
    CLASS("class", TokenGroup.KEYWORD),
    VAR("var", TokenGroup.KEYWORD),
    FUN("fun", TokenGroup.KEYWORD),
    RETURN("return", TokenGroup.KEYWORD),
    BREAK("break", TokenGroup.KEYWORD),
    CONTINUE("continue", TokenGroup.KEYWORD),
    NEW("new", TokenGroup.KEYWORD),
    IF("if", TokenGroup.KEYWORD),
    ELIF("elif", TokenGroup.KEYWORD),
    ELSE("else", TokenGroup.KEYWORD),
    FOR("for", TokenGroup.KEYWORD),
    WHILE("while", TokenGroup.KEYWORD),
    DO("do", TokenGroup.KEYWORD),
    IN("in", TokenGroup.KEYWORD),
    IMPORT("import", TokenGroup.KEYWORD),

    EQUALS("==", TokenGroup.LOGICAL),
    TRUE("true", TokenGroup.LOGICAL),
    FALSE("false", TokenGroup.LOGICAL),
    AND("&&", TokenGroup.LOGICAL),
    OR("||", TokenGroup.LOGICAL),
    NOT("!", TokenGroup.LOGICAL),

    NUMBER(group = TokenGroup.NUMERIC),
    PLUS("+", TokenGroup.NUMERIC),
    MINUS("-", TokenGroup.NUMERIC),
    MULTIPLY("*", TokenGroup.NUMERIC),
    IDIVIDE("//", TokenGroup.NUMERIC),
    DIVIDE("/", TokenGroup.NUMERIC),
    MOD("%", TokenGroup.NUMERIC),

    EQUAL("="),
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    LBRACKET("["),
    RBRACKET("]"),
    SEMICOLON(";"),
    RANGE(".."),
    COMMA(","),
    COLON(":"),
    AT("@"),
    DOT("."),

    STRING(group = TokenGroup.NONE),
    EMPTY(group = TokenGroup.NONE),
    ID(group = TokenGroup.NONE),
    SEARCH_GROUP(group = TokenGroup.NONE);

    companion object {
        val keywords = entries.filter { it.group == TokenGroup.KEYWORD && it.value.isNotBlank() }
        val logicals = entries.filter { it.group == TokenGroup.LOGICAL && it.value.isNotBlank() }
        val numerics = entries.filter { it.group == TokenGroup.NUMERIC && it.value.isNotBlank() }
        val other = entries.filter { it.group == TokenGroup.OTHER && it.value.isNotBlank() }
    }
}