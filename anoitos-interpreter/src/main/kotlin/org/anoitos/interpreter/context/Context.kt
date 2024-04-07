package org.anoitos.interpreter.context

import org.anoitos.interpreter.result.InterpretResult
import org.anoitos.parser.statement.statements.FunStatement

class Context(private val parent: Context? = null) {
    private var isFunContext: Boolean = false
    private val imports = mutableListOf<Module>()
    private val variables = mutableMapOf<String, Any>()
    private val functions = mutableListOf<FunStatement>()

    fun addImport(path: String) {
        TODO()
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
            TODO()
        }

        val context = Context(funParent)
        context.isFunContext = true

        for ((index, parameter) in function.parameters.withIndex()) {
            context.addVariable(parameter, arguments[index])
        }

        return when (val result = function.body.interpret(context)) {
            is InterpretResult.Return -> result.value
            else -> result
        }
    }

    fun getVariable(name: String): Any? =
        variables.getOrDefault(name, parent?.getVariable(name))

    fun getFunction(name: String): FunStatement? =
        functions.firstOrNull { it.name.value == name } ?: parent?.getFunction(name)

    fun getImports(): List<Module> =
        imports + (parent?.getImports() ?: emptyList())
}