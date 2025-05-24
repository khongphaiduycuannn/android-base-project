package com.ndmquan.base.project.ui.components.language_extendable_epoxy.adapter

import android.graphics.Color
import android.view.View
import androidx.databinding.DataBindingUtil
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.ndmquan.base.project.R
import com.ndmquan.base.project.databinding.ItemLanguageChildBinding
import com.ndmquan.base.project.ui.components.language_extendable_epoxy.model.ChildLanguageItem
import com.ndmquan.base_project.utils.extension.setPreventDoubleClick
import com.ndmquan.base_project.utils.parseColor

@EpoxyModelClass
abstract class ChildLanguageItemModel : EpoxyModelWithHolder<ChildLanguageItemModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.item_language_child


    @EpoxyAttribute
    lateinit var childLanguageItem: ChildLanguageItem

    @EpoxyAttribute(hash = false)
    var onClickListener: ((ChildLanguageItem) -> Unit)? = null


    override fun bind(holder: Holder) {
        holder.binding?.apply {
            ivThumbnail.setImageResource(childLanguageItem.thumbnail)
            tvName.text = childLanguageItem.name

            rbSelected.isChecked = childLanguageItem.isSelected
            item.strokeColor =
                if (childLanguageItem.isSelected) "#5A5A5A".parseColor() else Color.TRANSPARENT

            root.setPreventDoubleClick {
                onClickListener?.invoke(childLanguageItem)
            }
        }
    }


    override fun unbind(holder: Holder) {
        holder.binding?.root?.setOnClickListener(null)
    }


    inner class Holder : EpoxyHolder() {
        var binding: ItemLanguageChildBinding? = null

        override fun bindView(itemView: View) {
            binding = DataBindingUtil.bind(itemView)
        }
    }
}