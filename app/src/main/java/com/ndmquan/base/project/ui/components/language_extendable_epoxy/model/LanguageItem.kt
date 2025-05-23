package com.ndmquan.base.project.ui.components.language_extendable_epoxy.model

data class LanguageItem(
    val thumbnail: Int,
    val name: String,
    val description: String,
    val children: List<ChildLanguageItem> = emptyList(),
    val isExpanded: Boolean = false
) {

    val isSelected get() = children.any { it.isSelected }

    val singleChild get() = children.size == 1
}