package org.anoitos.interpreter.context

import org.anoitos.interpreter.InterpretResult
import org.anoitos.interpreter.extensions.interpret
import org.anoitos.interpreter.library.Library
import org.anoitos.interpreter.library.LibraryRegistry
import org.anoitos.parser.statement.statements.ClassStatement
import org.anoitos.parser.statement.statements.FunStatement

@Suppress("MemberVisibilityCanBePrivate")
class Context(
    private val parent: Context? = null,
    private var isFunctionContext: Boolean = false
) {
    private val imports = mutableSetOf<Library>()
    private val classes = mutableMapOf<String, Context>()
    private val functions = mutableMapOf<String, FunStatement>()
    private val variables = mutableMapOf<String, ContextVariable>()

    fun addImport(path: String) {
        if (parent != null) {
            throw IllegalStateException("Can't add an import to a context that has a parent")
        }

        val library = LibraryRegistry[path] ?: throw IllegalStateException("Library '${path}' not found")

        if (imports.contains(library)) {
            throw IllegalStateException("Can't add the same import twice")
        }

        imports.add(library)
    }

    fun addClass(name: String, context: Context) {
        if (getClass(name) != null) {
            throw IllegalStateException("Class '${name} already exists")
        }

        classes[name] = context
    }

    fun addClass(statement: ClassStatement) {
        val className = statement.name.value

        if (getClass(className) != null) {
            throw IllegalStateException("Class '${className} already exists")
        }

        classes[className] = Context(parent = this).apply {
            for (funStatement in statement.functions) {
                addFunction(funStatement)
            }

            for (varStatement in statement.variables) {
                addVariable(varStatement.name.value, varStatement.value.interpret(this)!!)
            }
        }
    }

    fun addFunction(statement: FunStatement) {
        val functionName = statement.name.value

        if (getFunction(functionName) != null) {
            throw IllegalStateException("Function '${functionName}' already exists")
        }

        functions[functionName] = statement
    }

    fun addVariable(name: String, value: Any) {
        if (getVariable(name) != null) {
            throw IllegalStateException("Variable '${name}' already exists")
        }

        variables[name] = ContextVariable(value)
    }

    fun setVariable(name: String, value: Any) {
        val variable = getVariable(name) ?: throw IllegalStateException("Variable '${name}' not found")

        variable.value = value
    }

    fun executeFunction(name: String, arguments: List<Any>): Any? {
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
            }

            throw IllegalStateException("Function '${name}' not found")
        }

        var functionParent: Context = this

        var loopParent: Context? = parent
        while (loopParent != null) {
            if (loopParent.isFunctionContext) {
                functionParent = loopParent.parent!!
            }

            loopParent = loopParent.parent
        }

        val context = Context(functionParent, true)

        return function.let {
            for ((index, parameter) in it.parameters.withIndex()) {
                context.addVariable(parameter, arguments[index])
            }

            when (val result = it.body.interpret(context)) {
                is InterpretResult.Return -> result.value
                else -> null
            }
        }
    }

    fun getImports(): Set<Library> {
        return imports + (parent?.getImports() ?: emptySet())
    }

    fun getClass(name: String): Context? {
        return if (classes.containsKey(name)) {
            classes[name]!!
        } else {
            parent?.getClass(name)
        }
    }

    fun getFunction(name: String): FunStatement? {
        return if (functions.containsKey(name)) {
            functions[name]!!
        } else {
            parent?.getFunction(name)
        }
    }

    fun getVariable(name: String): ContextVariable? {
        return if (variables.containsKey(name)) {
            variables[name]!!
        } else {
            parent?.getVariable(name)
        }
    }

    fun createInstance(): Context {
        return Context(parent).apply {
            for (function in functions) {
                addFunction(function.value)
            }

            for (variable in variables) {
                addVariable(variable.key, variable.value)
            }

            for (`class` in classes) {
                addClass(`class`.key, `class`.value)
            }
        }
    }
}