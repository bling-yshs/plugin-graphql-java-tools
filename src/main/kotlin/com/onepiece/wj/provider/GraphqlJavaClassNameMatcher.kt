package com.onepiece.wj.provider


/**
 * the relationship in Graphql type name with Java class name
 */
fun gqlMatch(typeName: String, className: String): Boolean {
    return className.matches(Regex(".*(?i)${typeName}(Resolver)?"))
}