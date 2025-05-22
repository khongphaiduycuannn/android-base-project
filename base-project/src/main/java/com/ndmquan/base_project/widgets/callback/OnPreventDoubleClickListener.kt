package com.ndmquan.base_project.widgets.callback

import android.view.View

class OnPreventDoubleClickListener(
    val onClickAction: (view: View?) -> Unit
) : View.OnClickListener {

    companion object {
        const val DEBOUNCE_TIME = 300L
        private var lastClickTime: Long = 0
    }

    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastClickTime < DEBOUNCE_TIME) {
            return
        }

        lastClickTime = currentTime

        onClickAction.invoke(v)
    }
}
