package com.duycuannn.demo_base.ui.language

import com.duycuannn.base_project.components.BaseViewModel
import com.duycuannn.base_project.utils.LanguageUtils
import com.duycuannn.demo_base.ui.language.components.LanguageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val languageUtils: LanguageUtils
) : BaseViewModel() {

    private val _languages = mutableListOf(
        LanguageItem(name = "English", iso = "en"),
        LanguageItem(name = "Hindi", iso = "hi"),
        LanguageItem(name = "Arabic", iso = "ar"),
        LanguageItem(name = "Spanish", iso = "es"),
        LanguageItem(name = "Croatian", iso = "hr"),
        LanguageItem(name = "Czech", iso = "cs"),
        LanguageItem(name = "Dutch", iso = "nl"),
        LanguageItem(name = "Filipino", iso = "fil"),
        LanguageItem(name = "French", iso = "fr"),
        LanguageItem(name = "German", iso = "de"),
        LanguageItem(name = "Indonesian", iso = "in"),
        LanguageItem(name = "Italian", iso = "it"),
        LanguageItem(name = "Japanese", iso = "ja"),
        LanguageItem(name = "Korean", iso = "ko"),
        LanguageItem(name = "Malay", iso = "ms"),
        LanguageItem(name = "Polish", iso = "pl"),
        LanguageItem(name = "Portuguese", iso = "pt"),
        LanguageItem(name = "Russian", iso = "ru"),
        LanguageItem(name = "Serbian", iso = "sr"),
        LanguageItem(name = "Swedish", iso = "sv"),
        LanguageItem(name = "Turkish", iso = "tr"),
        LanguageItem(name = "Vietnamese", iso = "vi"),
        LanguageItem(name = "China", iso = "zh")
    )

    private val _displayLanguages = MutableStateFlow<List<LanguageItem>>(emptyList())
    val displayLanguages: StateFlow<List<LanguageItem>> = _displayLanguages

    private val _selectedLanguage = MutableStateFlow(languageUtils.currentLanguage)


    init {
        initDisplayLanguages()
    }


    fun selectLanguage(iso: String) {
        _selectedLanguage.update { iso }
        _displayLanguages.update { it.map { item -> item.copy(selected = item.iso == iso) } }
    }

    fun applyLanguage() {
        languageUtils.setLanguage(_selectedLanguage.value)
    }


    private fun initDisplayLanguages() {
        val systemLanguageIso = languageUtils.systemLanguage
        val systemLanguage = _languages.find { it.iso == systemLanguageIso } ?: return

        val result = _languages.take(10).toMutableList()
        if (result.contains(systemLanguage)) {
            result.remove(systemLanguage)
            result.add(0, systemLanguage)
        } else {
            result.add(0, systemLanguage)
        }

        _displayLanguages.update { result }
        selectLanguage(languageUtils.currentLanguage)
    }
}