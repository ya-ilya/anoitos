package org.anoitos.interpreter.result

sealed interface InterpretResult {
    data object Break : InterpretResult
    data object Continue : InterpretResult
    data class Return(val value: Any?) : InterpretResult
}