package com.onepiece.wj.provider

import com.intellij.lang.jsgraphql.ide.search.GraphQLPsiSearchHelper
import com.intellij.lang.jsgraphql.psi.GraphQLFieldDefinition
import com.intellij.psi.*
import com.onepiece.wj.util.Icons
import com.onepiece.wj.util.lowercaseFirstLetter
import javax.swing.Icon

/**
 * Navigation method in Java Resolver to target Graphql Query/Mutation
 * @author: guoyanjiang
 * @date: 2023-10-18 18:31
 **/
class ResolverLineMarkerProvider : SimpleLineMarkerProvider<PsiNamedElement>() {
    override fun isTheElement(element: PsiElement): Boolean {
        if (element is PsiIdentifier
            && element.parent is PsiMethod
        ) {
            return checkPsiClassImplResolver((element.parent as PsiMethod).containingClass!!)
        }
        return false
    }

    private fun checkPsiClassImplResolver(psiClass: PsiClass): Boolean {
        val interfaces = psiClass.interfaces
        val result = interfaces.any {
            it.name?.endsWith("Resolver") ?: false
        }

        if (result) {
            return true
        }

        //try to search by extends classes
        val extends = psiClass.extendsList
        if (extends != null) {
            return extends.referenceElements.any {
                checkPsiClassImplResolver(it.resolve() as PsiClass)
            }
        }
        return false
    }

    override fun search(from: PsiElement): Array<PsiNamedElement> {
        val result: MutableList<PsiNamedElement> = mutableListOf()
        val gqlSearchHelper = GraphQLPsiSearchHelper.getInstance(from.project)
        val targetClass = (from.parent as PsiMethod).containingClass
        val searchName = from.text.substringAfter("get").lowercaseFirstLetter()
        gqlSearchHelper.processNamedElements(from, searchName) {
            if (it is GraphQLFieldDefinition) {
                val typeName = findGraphqlFieldParentTypeName(it)
                if (gqlMatch(typeName, targetClass)) {
                    result.add(it)
                }
            }
            true
        }
        return result.toTypedArray()
    }

    override fun getTooltip(from: PsiElement, target: PsiNamedElement): String {
        val typeName = findGraphqlFieldParentTypeName(target)
        val filePath = target.containingFile.virtualFile.path.substringAfter(target.project.name + "/")
        return "$filePath $typeName#${target.name}"
    }

    override fun getIcon(): Icon {
        return Icons.JAVA_LINE_MARKER.icon
    }
}