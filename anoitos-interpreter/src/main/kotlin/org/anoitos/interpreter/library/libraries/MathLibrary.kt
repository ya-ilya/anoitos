package org.anoitos.interpreter.library.libraries

import org.anoitos.interpreter.library.Library
import org.anoitos.interpreter.library.LibraryFunction
import org.anoitos.interpreter.result.InterpretResult
import kotlin.math.pow

@Suppress("unused")
object MathLibrary : Library() {
    @LibraryFunction
    fun plus(num1: Number, num2: Number): InterpretResult {
        return InterpretResult.Return(
            when {
                num1 is Double || num2 is Double -> num1.toDouble() + num2.toDouble()
                else -> num1.toInt() + num2.toInt()
            }
        )
    }

    @LibraryFunction
    fun minus(num1: Number, num2: Number): InterpretResult {
        return InterpretResult.Return(
            when {
                num1 is Double || num2 is Double -> num1.toDouble() - num2.toDouble()
                else -> num1.toInt() - num2.toInt()
            }
        )
    }

    @LibraryFunction
    fun multiply(num1: Number, num2: Number): InterpretResult {
        return InterpretResult.Return(
            when {
                num1 is Double || num2 is Double -> num1.toDouble() * num2.toDouble()
                else -> num1.toInt() * num2.toInt()
            }
        )
    }

    @LibraryFunction
    fun divide(num1: Number, num2: Number): InterpretResult {
        return InterpretResult.Return(
            when {
                num1 is Double || num2 is Double -> num1.toDouble() / num2.toDouble()
                else -> num1.toInt() / num2.toInt()
            }
        )
    }

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