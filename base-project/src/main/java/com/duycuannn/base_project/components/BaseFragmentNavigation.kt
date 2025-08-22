package com.duycuannn.base_project.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.duycuannn.base_project.utils.extensions.safeNavigateUp
import com.duycuannn.base_project.utils.logging.Logging

abstract class BaseFragmentNavigation<VB : ViewBinding>(
    bindingInflater: (LayoutInflater) -> VB
) : BaseFragment<VB>(bindingInflater) {

    protected val navController by lazy { findNavController() }


    private var backPressedCallback: OnBackPressedCallback? = null

    protected val previousBackStackDestinationId: Int?
        get() = try {
            if (isAdded && view != null) {
                navController.previousBackStackEntry?.destination?.id
            } else null
        } catch (ex: Exception) {
            Logging.e("Exception: ${ex.message}")
            null
        }

    protected val isLastEntry: Boolean
        get() = previousBackStackDestinationId == null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBackPressedDispatcher()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backPressedCallback?.remove()
        backPressedCallback = null
    }


    protected open fun onBackPressed() {
        if (isLastEntry) {
            activity?.finish()
        } else {
            safeNavigateUp()
        }
    }

    private fun setOnBackPressedDispatcher() {
        if (backPressedCallback == null) {
            backPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
        }
        backPressedCallback?.let {
            activity
                ?.onBackPressedDispatcher
                ?.addCallback(this, it)
        }
    }
}