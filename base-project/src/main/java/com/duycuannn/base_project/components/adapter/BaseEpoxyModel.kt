package com.duycuannn.base_project.components.adapter

import android.view.View
import android.view.ViewParent
import androidx.viewbinding.ViewBinding
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.duycuannn.base_project.utils.logging.Logging

abstract class BaseEpoxyModel<VB : ViewBinding>(
    private val binder: (View) -> VB,
    private val defaultLayout: Int
) : EpoxyModelWithHolder<BaseEpoxyHolder<*>>() {

    override fun getDefaultLayout(): Int = defaultLayout

    override fun bind(holder: BaseEpoxyHolder<*>) {
        super.bind(holder)
        val binding = holder.binding as VB
        bind(binding)
    }

    override fun createNewHolder(parent: ViewParent): BaseEpoxyHolder<VB>? {
        val holder = BaseEpoxyHolder(binder)
        Logging.e("$holder created")
        return holder
    }

    abstract fun bind(binding: VB)
}
