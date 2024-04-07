package org.anoitos.interpreter.library

import org.anoitos.interpreter.library.libraries.StandardLibrary

object LibraryRegistry {
    val libraries = mutableMapOf<String, Library>()

    init {
        this["standard"] = StandardLibrary
    }

    operator fun set(name: String, library: Library) {
        if (libraries.containsKey(name)) {
            throw RuntimeException()
        }

        libraries[name] = library
    }
}