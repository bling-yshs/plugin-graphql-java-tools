package com.onepiece.wj.util

import ai.grazie.utils.capitalize


fun String.lowercaseFirstLetter(): String {
    return processStringFirstLetter(this) { it.lowercase() }
}

fun String.capitalizeFirstLetter(): String {
    return processStringFirstLetter(this) { it.capitalize() }
}

fun processStringFirstLetter(input: String, action: (String) -> String): String {
    if (input.isEmpty()) {
        return input
    }

    val firstChar = input.substring(0, 1)
    val remainingChars = input.substring(1)

    return action(firstChar) + remainingChars
}

