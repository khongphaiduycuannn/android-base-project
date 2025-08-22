buildscript {

    dependencies {
        val navVersion = "2.8.9"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")

        val hiltVersion = "2.57"
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")

        val kspVersion = "2.1.0-1.0.29"
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:$kspVersion")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
}