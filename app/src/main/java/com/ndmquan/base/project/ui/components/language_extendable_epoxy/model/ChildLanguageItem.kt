package com.ndmquan.base.project.ui.components.language_extendable_epoxy.model

data class ChildLanguageItem(
    val thumbnail: Int,
    val name: String,
    val iso: String,
    var isSelected: Boolean = false
)