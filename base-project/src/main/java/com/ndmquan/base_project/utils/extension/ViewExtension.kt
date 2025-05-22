package com.ndmquan.base_project.utils.extension

import android.view.View

const val DEBOUNCE_TIME = 300L

var lastClickTime: Long = 0

fun View.setPreventDoubleClick(debounceTime: Long = DEBOUNCE_TIME, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {

        override fun onClick(v: View?) {
            if (System.currentTimeMillis() - lastClickTime < debounceTime) return

            action.invoke()
            lastClickTime = System.currentTimeMillis()
        }
    })
}


fun View.goneView() {
    visibility = View.GONE
}

fun View.visibleView() {
    visibility = View.VISIBLE
}

fun View.invisibleView() {
    visibility = View.INVISIBLE
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