package com.onepiece.wj.provider

import com.intellij.psi.PsiClass
import com.intellij.psi.impl.source.PsiClassReferenceType


/**
 * the relationship in Graphql type name with Java class name
 */
fun gqlMatch(typeName: String, targetClass: PsiClass?): Boolean {
    return nameMatch(targetClass, typeName) || typeMatch(targetClass, typeName)
}

private fun typeMatch(targetClass: PsiClass?, typeName: String) = targetClass?.interfaceTypes?.any {
    it.typeArguments().any {
        return if (it is PsiClassReferenceType) {
            it.name == typeName
        } else {
            println("unhandled type ${it.javaClass.name}")
            false
        }
    }
} ?: false

private fun nameMatch(targetClass: PsiClass?, typeName: String) =
    targetClass?.name?.matches(Regex(".*(?i)${typeName}(Resolver)?")) ?: false