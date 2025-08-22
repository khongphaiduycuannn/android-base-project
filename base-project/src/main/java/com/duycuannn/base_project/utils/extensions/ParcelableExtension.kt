package com.duycuannn.base_project.utils.extensions

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.core.content.IntentCompat
import androidx.core.os.BundleCompat
import kotlin.jvm.java

inline fun <reified T : Parcelable> Intent.getParcelable(key: String): T? = when {
    isSdk33OrAbove -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Intent.getParcelableArrayList(key: String): ArrayList<T>? = when {
    isSdk33OrAbove -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}

inline fun <reified T : Parcelable> Bundle.getParcelable(key: String): T? = when {
    isSdk33OrAbove -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? =
    BundleCompat.getParcelable(this, key, T::class.java)

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? =
    IntentCompat.getParcelableExtra(this, key, T::class.java)