package com.ndmquan.base_project.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.ndmquan.base_project.utils.extension.getScreenWidth

abstract class BaseDialog<VB : ViewBinding>(
    context: Context,
    private val bindingInflater: (LayoutInflater) -> VB,
    private val gravity: Int = Gravity.CENTER
) : Dialog(context) {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding as VB


    open val isCancelable = false


    open fun getWidthPercent(): Float = 0.85F

    abstract fun onViewReady()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        setCancelable(isCancelable)
        setCanceledOnTouchOutside(isCancelable)
        onViewReady()
    }

    override fun show() {
        super.show()
        val width = getScreenWidth() * getWidthPercent()
        window?.setLayout(width.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setGravity(gravity)
    }
}