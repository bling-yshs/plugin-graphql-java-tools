package com.onepiece.wj.provider

import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.search.ProjectScope
import com.intellij.psi.search.searches.ClassInheritorsSearch
import com.intellij.psi.util.elementType
import com.onepiece.wj.util.Icons
import com.onepiece.wj.util.capitalizeFirstLetter
import java.net.URLConnection
import javax.swing.Icon


/**
 * Navigation Graphql Query/Mutation to target method in Java Resolver
 * @author: guoyanjiang
 * @date: 2023-10-18 18:30
 **/
class GraphqlLineMarkerProvider : SimpleLineMarkerProvider<PsiMethod>() {

    override fun isTheElement(element: PsiElement): Boolean {
        if (element is LeafPsiElement
            && element.elementType.debugName.equals("NAME")
            && element.parent.parent.elementType?.debugName.equals("FIELD_DEFINITION")
        ) {
            return true
        }
        return false
    }

    override fun search(from: PsiElement): Array<PsiMethod> {
        val allScope = ProjectScope.getAllScope(from.project)
        val psiFacade = JavaPsiFacade.getInstance(from.project)
        val interfaceClass = psiFacade.findClass("graphql.kickstart.tools.GraphQLResolver", allScope)
        val typeName = findGraphqlFieldParentTypeName(from)
        if (interfaceClass != null) {
            val targetClasses = ClassInheritorsSearch.search(interfaceClass).filter {
                gqlMatch(typeName, it.name!!)
            }
            val result: MutableList<PsiMethod> = mutableListOf()

            for (targetClass in targetClasses) {
                var elements = targetClass.findMethodsByName(from.text, true)
                if (elements.isEmpty()) {
                    elements = targetClass.findMethodsByName("get${from.text.capitalizeFirstLetter()}", true)
                }
                result.addAll(elements)
            }
            return result.toTypedArray()
        }
        return emptyArray()
    }

    override fun getTooltip(from: PsiElement, target: PsiMethod): String {
        return target.containingClass?.name + "#" + target.name
    }

    override fun getIcon(): Icon {
        return Icons.GQL_LINE_MARKER.icon
    }
}