package org.anoitos.interpreter.context

import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.library.Library
import org.anoitos.interpreter.library.LibraryRegistry
import org.anoitos.interpreter.result.InterpretResult
import org.anoitos.parser.statement.statements.ClassStatement
import org.anoitos.parser.statement.statements.FunStatement

class Context(private val parent: Context? = null) {
    private var isFunContext: Boolean = false
    private val imports = mutableListOf<Library>()
    private val variables = mutableMapOf<String, Any>()
    private val classes = mutableMapOf<String, Context>()
    private val functions = mutableListOf<FunStatement>()

    fun addImport(path: String) {
        imports.add(LibraryRegistry.libraries[path]!!)
    }

    fun addVariable(name: String, value: Any) {
        check(getVariable(name) == null)
        variables[name] = value
    }

    fun setVariable(name: String, value: Any) {
        var parent: Context? = this

        while (parent != null) {
            if (parent.variables.containsKey(name)) {
                parent.variables[name] = value
                break
            } else {
                parent = parent.parent
            }
        }
    }

    fun addClass(classStatement: ClassStatement) {
        if (getClass(classStatement.name.value) != null) {
            throw Exception()
        }
        classes[classStatement.name.value] = Context(this).also {
            for (funStatement in classStatement.functions) {
                it.addFunction(funStatement)
            }

            for (varStatement in classStatement.variables) {
                it.addVariable(varStatement.name.value, varStatement.value.interpret(it)!!)
            }
        }
    }

    fun addFunction(funStatement: FunStatement) {
        if (getFunction(funStatement.name.value) != null) {
            throw Exception()
        }
        functions.add(funStatement)
    }

    fun executeFunction(name: String, arguments: List<Any>): Any? {
        var funParent = this
        var parent: Context? = parent
        while (parent != null) {
            if (parent.isFunContext) {
                funParent = parent.parent!!
            }

            parent = parent.parent
        }

        val function = getFunction(name)

        if (function == null) {
            val method = getImports()
                .filter { it.getFunction(name) != null }
                .associateWith { it.getFunction(name)!! }
                .toList().getOrNull(0)

            if (method != null) {
                val methodArguments = mutableListOf<Any>()
                for ((index, parameter) in method.second.parameters.withIndex()) {
                    if (index == method.second.parameters.lastIndex && parameter.isVarArgs) {
                        methodArguments.add(
                            arguments
                                .drop(index)
                                .toTypedArray()
                        )
                        continue
                    }

                    methodArguments.add(arguments[index])
                }

                return when (val result = method.second.invoke(method.first, *methodArguments.toTypedArray())) {
                    is InterpretResult.Return -> result.value
                    else -> null
                }
            } else {
                throw Exception()
            }
        }

        val context = Context(funParent)
        context.isFunContext = true

        for ((index, parameter) in function.parameters.withIndex()) {
            context.addVariable(parameter, arguments[index])
        }

        return when (val result = function.body.interpret(context)) {
            is InterpretResult.Return -> result.value
            else -> null
        }
    }

    fun getVariable(name: String): Any? =
        variables.getOrDefault(name, parent?.getVariable(name))

    fun getClass(name: String): Context? =
        classes[name] ?: parent?.getClass(name)

    fun getFunction(name: String): FunStatement? =
        functions.firstOrNull { it.name.value == name } ?: parent?.getFunction(name)

    fun getImports(): List<Library> =
        imports + (parent?.getImports() ?: emptyList())
}