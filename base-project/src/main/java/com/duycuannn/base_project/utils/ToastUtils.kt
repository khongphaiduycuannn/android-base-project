package com.duycuannn.base_project.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlin.let

fun Context?.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    this?.let { context ->
        Toast.makeText(context, message, duration).show()
    }
}

fun Context?.showToast(stringResource: Int, duration: Int = Toast.LENGTH_SHORT) {
    this?.let { context ->
        val message = context.getString(stringResource)
        Toast.makeText(context, message, duration).show()
    }
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    this.context?.showToast(message, duration)
}

fun Fragment.showToast(stringResource: Int, duration: Int = Toast.LENGTH_SHORT) {
    this.context?.showToast(stringResource, duration)
}