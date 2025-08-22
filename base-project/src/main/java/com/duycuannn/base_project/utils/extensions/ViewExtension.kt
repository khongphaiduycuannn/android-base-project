package com.duycuannn.base_project.utils.extensions

import android.view.View

fun View.goneView() {
    visibility = View.GONE
}

fun View.visibleView() {
    visibility = View.VISIBLE
}

fun View.invisibleView() {
    visibility = View.INVISIBLE
}

fun View.visibleViewIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.invisibleViewIf(condition: Boolean) {
    visibility = if (condition) View.INVISIBLE else View.VISIBLE
}

fun View.isGone(): Boolean {
    return visibility == View.GONE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isInvisible(): Boolean {
    return visibility == View.INVISIBLE
}