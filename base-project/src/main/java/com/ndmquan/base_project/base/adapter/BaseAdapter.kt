package com.ndmquan.base_project.base.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    protected abstract val items: MutableList<T>


    open fun submitList(list: List<T>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = list.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areItemsTheSame(items[oldItemPosition], list[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areContentsTheSame(
                    items.elementAt(oldItemPosition), list.elementAt(newItemPosition)
                )
            }
        })
        diffResult.dispatchUpdatesTo(this)
        setNewData(list)
    }

    open fun setNewData(list: Collection<T>) {
        items.clear()
        items.addAll(list)
    }


    protected open fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    protected open fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}
