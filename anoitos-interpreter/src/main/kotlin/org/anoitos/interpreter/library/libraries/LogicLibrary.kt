package org.anoitos.interpreter.library.libraries

import org.anoitos.interpreter.library.Library
import org.anoitos.interpreter.library.LibraryFunction
import org.anoitos.interpreter.result.InterpretResult

@Suppress("unused")
object LogicLibrary : Library() {
    @LibraryFunction
    fun and(boolean1: Boolean, boolean2: Boolean): InterpretResult {
        return InterpretResult.Return(boolean1 && boolean2)
    }

    @LibraryFunction
    fun or(boolean1: Boolean, boolean2: Boolean): InterpretResult {
        return InterpretResult.Return(boolean1 || boolean2)
    }

    @LibraryFunction
    fun not(boolean1: Boolean): InterpretResult {
        return InterpretResult.Return(!boolean1)
    }

    @LibraryFunction
    fun equals(any1: Any?, any2: Any?): InterpretResult {
        return InterpretResult.Return(any1 == any2)
    }
}