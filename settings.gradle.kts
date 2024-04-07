rootProject.name = "anoitos"

pluginManagement {
    val kotlinVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

include(
    "anoitos-lexer",
    "anoitos-parser"
)