package com.ndmquan.base_project.utils.extension

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.ndmquan.base_project.databinding.DialogDefaultLoadingBinding

fun Dialog.quickShow(
    lifecycle: LifecycleOwner,
    binding: ViewBinding,
    isTouchOutSideCancelable: Boolean = false
) {
    if (lifecycle.lifecycle.currentState == androidx.lifecycle.Lifecycle.State.DESTROYED)
        return
    setContentView(binding.root)
    window?.apply {
        setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        attributes.gravity = Gravity.CENTER
    }
    setCancelable(isTouchOutSideCancelable)
    setCanceledOnTouchOutside(isTouchOutSideCancelable)
    show()
}

fun Dialog.showLoading(
    lifecycle: LifecycleOwner,
    binding: ViewBinding = DialogDefaultLoadingBinding.inflate(layoutInflater),
    isTouchOutSideCancelable: Boolean = false
) {
    quickShow(lifecycle, binding, isTouchOutSideCancelable)
}