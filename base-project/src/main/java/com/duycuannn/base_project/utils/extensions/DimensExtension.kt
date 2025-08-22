package com.duycuannn.base_project.utils.extensions

import android.content.res.Resources

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)


fun getScreenWidth(): Int = Resources.getSystem().displayMetrics.widthPixels

fun getScreenHeight(): Int = Resources.getSystem().displayMetrics.heightPixels