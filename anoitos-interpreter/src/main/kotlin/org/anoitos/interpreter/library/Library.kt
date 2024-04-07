package org.anoitos.interpreter.library

abstract class Library {
    private val functions = javaClass.declaredMethods
        .filter { it.isAnnotationPresent(LibraryFunction::class.java) }
        .onEach { it.isAccessible = true }

    fun getFunction(name: String) = functions.firstOrNull { it.name == name }
}