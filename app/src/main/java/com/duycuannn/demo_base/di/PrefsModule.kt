package com.duycuannn.demo_base.di

import android.content.Context
import android.content.SharedPreferences
import com.duycuannn.base_project.utils.LanguageUtils
import com.duycuannn.base_project.utils.constants.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefsModule {

    @Provides
    @Singleton
    fun providePrefs(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.DEFAULT_PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideLanguageUtils(@ApplicationContext context: Context): LanguageUtils {
        return LanguageUtils(context)
    }
}