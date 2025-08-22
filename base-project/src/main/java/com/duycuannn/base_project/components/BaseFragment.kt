package com.duycuannn.base_project.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding as VB


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
        _binding = null
        super.onDestroyView()
    }


    protected open fun initData() {}
    protected open fun initViews() {}
    protected open fun handleEvents() {}
    protected open fun observeData() {}
}