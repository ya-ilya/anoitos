package org.anoitos.interpreter.library.libraries

import org.anoitos.interpreter.library.Library
import org.anoitos.interpreter.library.LibraryFunction
import org.anoitos.interpreter.result.InterpretResult

@Suppress("unused")
object LogicLibrary : Library() {
    @LibraryFunction
    fun equals(any1: Any?, any2: Any?): InterpretResult {
        return InterpretResult.Return(any1 == any2)
    }
}