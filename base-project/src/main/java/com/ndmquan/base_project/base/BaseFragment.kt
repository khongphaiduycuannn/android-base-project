package com.ndmquan.base_project.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.ndmquan.base_project.utils.LocalStorageUtils

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding as VB


    protected open val prefs by lazy { context?.let { LocalStorageUtils.defaultPrefs(it) } }


    protected open val viewModel: BaseViewModel by viewModels()


    private var backPressedCallback: OnBackPressedCallback? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        handleBackPressedEvent()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initData()
        _binding = bindingInflater(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvents()
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backPressedCallback?.remove()
        backPressedCallback = null
        _binding = null
    }

    private fun handleBackPressedEvent() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val isLastEntry = findNavController().previousBackStackEntry == null
                onBackPressed(isLastEntry)
            }
        }
        backPressedCallback?.let { activity?.onBackPressedDispatcher?.addCallback(this, it) }
    }


    protected open fun onBackPressed(isLastEntry: Boolean) {
        if (isLastEntry) {
            requireActivity().finish()
        } else {
            findNavController().navigateUp()
        }
    }


    protected open fun initData() {}

    protected open fun initViews() {}

    protected open fun handleEvents() {}

    protected open fun observeData() {}
}