package com.duycuannn.base_project.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.duycuannn.base_project.utils.constants.Constants.DEFAULT_PREFS_NAME
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.Locale
import javax.inject.Singleton

@Singleton
class LanguageUtils(
    private val context: Context
) {

    companion object {
        private const val LANGUAGE_KEY = "language_key"
        private const val DEFAULT_LANGUAGE = "en"


        private val _languageChangedEvent = MutableSharedFlow<String>(
            replay = 0,
            extraBufferCapacity = 2,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
        val languageChangedEvent: SharedFlow<String> = _languageChangedEvent.asSharedFlow()
    }


    private val prefs: SharedPreferences =
        context.getSharedPreferences(DEFAULT_PREFS_NAME, Context.MODE_PRIVATE)


    val currentLanguage: String
        get() = prefs.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE

    val currentLocale: Locale
        get() = Locale.Builder().setLanguage(currentLanguage).build()


    val systemLanguage: String
        get() = context.resources.configuration.locales[0].language


    fun setLanguage(language: String) {
        val prevLanguage = prefs.getString(LANGUAGE_KEY, null)
        prefs.edit { putString(LANGUAGE_KEY, language) }

        if (language != prevLanguage) {
            notifyLanguageChange()
        }
    }


    private fun notifyLanguageChange() {
        _languageChangedEvent.tryEmit(currentLanguage)
    }
}