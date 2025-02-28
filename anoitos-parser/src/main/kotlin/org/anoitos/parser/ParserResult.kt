package org.anoitos.parser

import org.anoitos.parser.element.ParserElement

data class ParserResult<out T : ParserElement>(
    val size: Int,
    val element: T
)