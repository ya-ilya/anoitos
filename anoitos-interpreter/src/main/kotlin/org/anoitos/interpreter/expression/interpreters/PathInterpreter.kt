package org.anoitos.interpreter.expression.interpreters

import org.anoitos.interpreter.context.Context
import org.anoitos.interpreter.expression.ExpressionInterpreter
import org.anoitos.parser.element.elements.TokenElement
import org.anoitos.parser.expression.expressions.CallExpression
import org.anoitos.parser.expression.expressions.PathExpression

object PathInterpreter : ExpressionInterpreter<PathExpression> {
    override fun interpret(expression: PathExpression, context: Context): Any? {
        var currentValue: Any? = null

        for (element in expression.elements) {
            val currentContext = if (currentValue is Context) currentValue else context

            when (element) {
                is TokenElement -> {
                    val variable = currentContext.getVariable(element.token.value)

                    if (variable != null) {
                        currentValue = variable.value
                        break
                    }

                    val classContext = currentContext.getClass(element.token.value)

                    if (classContext != null) {
                        currentValue = classContext
                        break
                    }

                    throw IllegalStateException("Unknown token type")
                }

                is CallExpression -> {
                    currentValue = CallInterpreter.interpret(element, currentContext)
                }
            }
        }

        return currentValue
    }
}