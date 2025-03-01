package org.anoitos.interpreter.library

import org.anoitos.interpreter.library.libraries.IOLibrary
import org.anoitos.interpreter.library.libraries.LogicLibrary
import org.anoitos.interpreter.library.libraries.MathLibrary

object LibraryRegistry {
    val libraries = mutableMapOf<String, Library>()

    init {
        this["io"] = IOLibrary
        this["logic"] = LogicLibrary
        this["math"] = MathLibrary
    }

    operator fun get(name: String): Library? {
        return libraries[name]
    }

    operator fun set(name: String, library: Library) {
        if (libraries.containsKey(name)) {
            throw RuntimeException()
        }

        libraries[name] = library
    }
}