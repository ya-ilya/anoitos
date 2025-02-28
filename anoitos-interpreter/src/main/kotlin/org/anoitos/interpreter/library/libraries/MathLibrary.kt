package org.anoitos.interpreter.library.libraries

import org.anoitos.interpreter.library.Library
import org.anoitos.interpreter.library.LibraryFunction
import org.anoitos.interpreter.InterpretResult
import kotlin.math.pow

@Suppress("unused")
object MathLibrary : Library() {
    @LibraryFunction
    fun pow(num1: Number, num2: Number): InterpretResult {
        val result = num1.toDouble().pow(num2.toDouble())

        return InterpretResult.Return(
            when {
                num1 is Double || num2 is Double -> result
                else -> result.toInt()
            }
        )
    }
}