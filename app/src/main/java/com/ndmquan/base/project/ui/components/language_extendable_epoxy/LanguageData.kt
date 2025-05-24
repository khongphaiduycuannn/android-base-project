package com.ndmquan.base.project.ui.components.language_extendable_epoxy

import com.ndmquan.base.project.R
import com.ndmquan.base.project.ui.components.language_extendable_epoxy.model.ChildLanguageItem
import com.ndmquan.base.project.ui.components.language_extendable_epoxy.model.LanguageItem

object LanguageData {

    val languages = mutableListOf(
        LanguageItem(
            thumbnail = R.drawable.ic_language_english,
            name = "English",
            description = "English",
            isExpanded = false,
            children = listOf(
                ChildLanguageItem(R.drawable.ic_language_usa, "American", "en"),
                ChildLanguageItem(R.drawable.ic_language_english, "British", "en"),
                ChildLanguageItem(R.drawable.ic_language_hindi, "Indian", "en")
            )
        ),
        LanguageItem(
            R.drawable.ic_language_spanish,
            "Spanish",
            "Español",
            listOf(
                ChildLanguageItem(R.drawable.ic_language_spanish, "Castilian", "es"),
                ChildLanguageItem(R.drawable.ic_language_mexico, "Mexican", "es"),
                ChildLanguageItem(R.drawable.ic_language_spanish, "Rioplatense", "es")
            )
        ),
        LanguageItem(
            R.drawable.ic_language_portugal,
            "Portuguese",
            "Português",
            listOf(
                ChildLanguageItem(R.drawable.ic_language_brazil, "Brazilian", "pt"),
                ChildLanguageItem(R.drawable.ic_language_european, "European", "pt"),
                ChildLanguageItem(R.drawable.ic_language_portugal, "African", "pt")
            )
        ),
        LanguageItem(
            R.drawable.ic_language_arabic,
            "Arabic",
            "العربية",
            listOf(
                ChildLanguageItem(R.drawable.ic_language_arabic, "الفصحى", "ar"),
                ChildLanguageItem(R.drawable.ic_language_arabic, "المصرية", "ar"),
                ChildLanguageItem(R.drawable.ic_language_arabic, "الشامية", "ar")
            )
        ),
        LanguageItem(
            R.drawable.ic_language_hindi,
            "Hindi",
            "हिन्दी",
            listOf(ChildLanguageItem(R.drawable.ic_language_hindi, "Hindi", "hi"))
        ),
        LanguageItem(
            R.drawable.ic_language_france,
            "French",
            "Français",
            listOf(ChildLanguageItem(R.drawable.ic_language_france, "French", "fr"))
        ),
        LanguageItem(
            R.drawable.ic_language_korean,
            "Korean",
            "한국어",
            listOf(ChildLanguageItem(R.drawable.ic_language_korean, "Korean", "ko"))
        ),
        LanguageItem(
            R.drawable.ic_language_german,
            "German",
            "Deutsch",
            listOf(ChildLanguageItem(R.drawable.ic_language_german, "German", "de"))
        ),
        LanguageItem(
            R.drawable.ic_language_italian,
            "Italian",
            "Italiano",
            listOf(ChildLanguageItem(R.drawable.ic_language_italian, "Italian", "it"))
        )
    )


    fun toggleLanguageExpansion(languageItem: LanguageItem) {
        val index = languages.indexOfFirst { it.name == languageItem.name }
        if (index != -1) {
            languages[index] = languageItem.copy(isExpanded = !languageItem.isExpanded)
        }
    }

    fun selectChildLanguage(childLanguage: ChildLanguageItem) {
        val newLanguages = languages.map {
            it.copy(
                children = it.children.map { child ->
                    child.copy(isSelected = child.name == childLanguage.name)
                }
            )
        }
        languages.clear()
        languages.addAll(newLanguages)
    }
}