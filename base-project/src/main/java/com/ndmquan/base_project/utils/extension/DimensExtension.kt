package com.ndmquan.base_project.utils.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = (this / Resources.getSystem().displayMetrics.density)


val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.px: Float
    get() = (this * Resources.getSystem().displayMetrics.density)


fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}


@SuppressLint("InternalInsetResource")
fun Context.getStatusBarHeight(): Int {
    var result = 25.dp
    try {
        val resourceId: Int =
            resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return result
}

@SuppressLint("InternalInsetResource")
fun Context.getNavigationBarHeight(): Int {
    var result = 25.dp
    try {
        val resourceId: Int =
            resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return result
}
