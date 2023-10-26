package com.onepiece.wj.provider

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.lang.jsgraphql.psi.GraphQLFieldDefinition
import com.intellij.lang.jsgraphql.psi.GraphQLInterfaceTypeDefinition
import com.intellij.lang.jsgraphql.psi.GraphQLNamedTypeDefinition
import com.intellij.lang.jsgraphql.psi.GraphQLObjectTypeDefinition
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import javax.swing.Icon

/**
 * A Simple Abstract Class to do some general step in LineMarker.
 *
 * @see com.intellij.codeInsight.daemon.LineMarkerProvider.getLineMarkerInfo
 * @author: guoyanjiang
 * @date: 2023-10-18 17:18
 **/
abstract class SimpleLineMarkerProvider<T : PsiElement> : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        if (!isTheElement(element)) {
            return
        }
        val searchResult = search(element)
        if (searchResult.isNotEmpty()) {
            val navigationGutterIconBuilder = NavigationGutterIconBuilder.create(getIcon())
            navigationGutterIconBuilder.setTooltipTitle(getTooltip(element, searchResult[0]))
            navigationGutterIconBuilder.setTargets(*searchResult)
            val lineMarkerInfo = navigationGutterIconBuilder.createLineMarkerInfo(
                element
            )
            result.add(lineMarkerInfo)
        }
    }

    abstract fun isTheElement(element: PsiElement): Boolean

    abstract fun search(from: PsiElement): Array<T>

    abstract fun getTooltip(from: PsiElement, target: T): String

    abstract override fun getIcon(): Icon

    protected fun findGraphqlFieldParentTypeName(target: PsiElement): String {
        when (target) {
            is LeafPsiElement -> return findGraphqlFieldParentTypeName(target.parent.parent)
            is GraphQLFieldDefinition -> {
                val typeElement = target.parent.parent
                if (typeElement != null) {
                    when (typeElement) {
                        is GraphQLObjectTypeDefinition,
                        is GraphQLInterfaceTypeDefinition -> return getTypeName(typeElement as GraphQLNamedTypeDefinition)
                    }
                }
            }
        }
        return ""
    }

    private fun getTypeName(item: GraphQLNamedTypeDefinition): String {
        return item.typeNameDefinition?.name.toString()
    }

}
