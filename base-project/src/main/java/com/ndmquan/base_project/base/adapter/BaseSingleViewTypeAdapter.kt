package com.ndmquan.base_project.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

abstract class BaseSingleViewTypeAdapter<T, VB : ViewBinding> :
    BaseAdapter<T, BaseSingleViewTypeAdapter<T, VB>.BaseViewHolder>() {

    inner class BaseViewHolder(
        private val binding: VB
    ) : ViewHolder(binding.root) {

        fun bind(item: T, position: Int) {
            this@BaseSingleViewTypeAdapter.bind(item, position, binding)
        }
    }


    abstract fun bind(item: T, position: Int, binding: VB)


    abstract fun createBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ): VB

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = createBinding(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position], position)
    }
}