package com.duycuannn.base_project.components.adapter

import android.view.View
import androidx.viewbinding.ViewBinding
import com.airbnb.epoxy.EpoxyHolder

open class BaseEpoxyHolder<VB : ViewBinding>(
    private val binder: (View) -> VB
) : EpoxyHolder() {

    lateinit var binding: VB

    override fun bindView(itemView: View) {
        binding = binder.invoke(itemView)
    }
}