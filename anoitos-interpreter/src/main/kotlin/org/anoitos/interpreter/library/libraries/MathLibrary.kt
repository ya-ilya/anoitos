package org.anoitos.interpreter.library.libraries

import org.anoitos.interpreter.library.Library
import org.anoitos.interpreter.library.LibraryFunction
import org.anoitos.interpreter.result.InterpretResult
import kotlin.math.pow

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

    @LibraryFunction
    fun multiply(num1: Int, num2: Int): InterpretResult {
        return InterpretResult.Return(num1 * num2)
    }

    @LibraryFunction
    fun divide(num1: Int, num2: Int): InterpretResult {
        return InterpretResult.Return(num1 / num2)
    }

    @LibraryFunction
    fun pow(num1: Int, num2: Int): InterpretResult {
        return InterpretResult.Return(num1.toDouble().pow(num2.toDouble()).toInt())
    }
}