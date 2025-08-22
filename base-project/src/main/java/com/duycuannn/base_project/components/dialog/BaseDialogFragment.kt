package com.duycuannn.base_project.components.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.duycuannn.base_project.utils.extensions.getScreenWidth

abstract class BaseDialogFragment<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val gravity: Int = Gravity.CENTER
) : DialogFragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding as VB


    protected open val dimAmount: Float = 0.5f

    protected open val widthPercent: Float = 0.9f


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply { setupDialog() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initData()
        _binding = bindingInflater(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvents()
        observeData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    private fun Dialog.setupDialog() {
        setOnShowListener {
            val layoutParams = window?.attributes
            layoutParams?.width = (getScreenWidth() * widthPercent).toInt()
            window?.apply {
                attributes = layoutParams
                setBackgroundDrawableResource(android.R.color.transparent)
                setDimAmount(dimAmount)
                setGravity(gravity)
            }
        }
    }


    protected open fun initData() {}
    protected open fun initViews() {}
    protected open fun handleEvents() {}
    protected open fun observeData() {}
}