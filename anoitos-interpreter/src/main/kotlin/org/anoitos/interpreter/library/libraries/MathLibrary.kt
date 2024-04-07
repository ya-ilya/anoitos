package org.anoitos.interpreter.library.libraries

import org.anoitos.interpreter.library.Library
import org.anoitos.interpreter.library.LibraryFunction
import org.anoitos.interpreter.result.InterpretResult

@Suppress("unused")
object MathLibrary : Library() {
    @LibraryFunction
    fun plus(num1: Int, num2: Int): InterpretResult {
        return InterpretResult.Return(num1 + num2)
    }

    @LibraryFunction
    fun minus(num1: Int, num2: Int): InterpretResult {
        return InterpretResult.Return(num1 - num2)
    }
}