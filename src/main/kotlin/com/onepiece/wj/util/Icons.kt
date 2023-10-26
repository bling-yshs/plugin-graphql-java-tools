package com.onepiece.wj.util

import com.intellij.openapi.util.IconLoader.getIcon
import javax.swing.Icon

/**
 * @author: guoyanjiang
 * @date: 2023-10-18 17:15
 **/
enum class Icons(val icon: Icon) {

    GQL_LINE_MARKER(getIcon("/images/go.png", Icons::class.java)),
    Java_LINE_MARKER(getIcon("/images/return.png", Icons::class.java)),
    ;

}