package org.anoitos.interpreter.library.libraries

import org.anoitos.interpreter.library.Library
import org.anoitos.interpreter.library.LibraryFunction

@Suppress("unused")
object StandardLibrary : Library() {
    @LibraryFunction
    fun println(text: Any?) {
        kotlin.io.println(text)
    }

    @LibraryFunction
    fun print(text: Any?) {
        kotlin.io.print(text)
    }
}