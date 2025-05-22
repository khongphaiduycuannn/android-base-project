package com.ndmquan.base_project.utils.extension

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    isSdk33OrAbove -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    isSdk33OrAbove -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}