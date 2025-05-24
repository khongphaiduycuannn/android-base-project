package com.ndmquan.base.project.ui.components.language_extendable_epoxy.adapter

import com.airbnb.epoxy.EpoxyController
import com.ndmquan.base.project.ui.components.language_extendable_epoxy.model.ChildLanguageItem
import com.ndmquan.base.project.ui.components.language_extendable_epoxy.model.LanguageItem

class LanguageListController : EpoxyController() {

    private var languages = mutableListOf<LanguageItem>()

    private var onLanguageClickListener: ((LanguageItem) -> Unit)? = null

    private var onChildLanguageClickListener: ((ChildLanguageItem) -> Unit)? = null


    fun submitData(items: List<LanguageItem>) {
        languages.clear()
        languages.addAll(items)
        requestModelBuild()
    }

    fun setOnLanguageClickListener(listener: (LanguageItem) -> Unit) {
        onLanguageClickListener = listener
    }

    fun setOnChildLanguageClickListener(listener: (ChildLanguageItem) -> Unit) {
        onChildLanguageClickListener = listener
    }


    override fun buildModels() {
        languages.forEach { language ->
            languageItem {
                id(language.name)
                languageItem(language)
                onClickListener {
                    if (language.singleChild) {
                        this@LanguageListController
                            .onChildLanguageClickListener
                            ?.invoke(language.children.first())
                    } else {
                        this@LanguageListController
                            .onLanguageClickListener
                            ?.invoke(language)
                    }
                }
            }

            if (!language.isExpanded) return@forEach

            language.children.forEach { childLanguage ->
                childLanguageItem {
                    id("_${childLanguage.name}")
                    childLanguageItem(childLanguage)
                    onClickListener {
                        this@LanguageListController
                            .onChildLanguageClickListener
                            ?.invoke(childLanguage)
                    }
                }
            }
        }
    }
}