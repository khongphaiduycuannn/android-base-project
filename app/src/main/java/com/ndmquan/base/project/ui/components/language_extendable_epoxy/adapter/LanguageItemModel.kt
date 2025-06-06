package com.ndmquan.base.project.ui.components.language_extendable_epoxy.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.ndmquan.base.project.R
import com.ndmquan.base.project.databinding.ItemLanguageParentBinding
import com.ndmquan.base.project.ui.components.language_extendable_epoxy.model.LanguageItem
import com.ndmquan.base_project.utils.extension.goneView
import com.ndmquan.base_project.utils.extension.visibleView
import com.ndmquan.base_project.utils.parseColor

@EpoxyModelClass
abstract class LanguageItemModel : EpoxyModelWithHolder<LanguageItemModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.item_language_parent


    @EpoxyAttribute
    lateinit var languageItem: LanguageItem

    @EpoxyAttribute(hash = false)
    var onClickListener: ((LanguageItem) -> Unit)? = null


    override fun bind(holder: Holder) {
        holder.binding?.apply {
            ivLanguage.setImageResource(languageItem.thumbnail)
            tvName.text = languageItem.name
            tvDescription.text = languageItem.description

            item.strokeColor =
                if (languageItem.isSelected && !languageItem.isExpanded) "#5A5A5A".parseColor() else Color.TRANSPARENT
            rbSelected.isChecked = languageItem.isSelected

            if (languageItem.singleChild) {
                flChildContainer.goneView()
                ivExpandIcon.goneView()
                rbSelected.visibleView()
            } else {
                flChildContainer.visibleView()
                ivExpandIcon.visibleView()
                rbSelected.goneView()
            }

            val childThumbs = languageItem.children.map { it.thumbnail }.distinct().take(3)
            val imageViews = flChildContainer.children.map { it as ImageView }.toList().reversed()
            (0..2).forEach { index ->
                imageViews[index].setImageResource(if (index < childThumbs.size) childThumbs[index] else R.color.white)
            }

            executePendingBindings()

            root.setOnClickListener {
                onClickListener?.invoke(languageItem)
            }

            val rotationAngle = if (languageItem.isExpanded) -90f else 90f
            ivExpandIcon.animate()
                .rotation(rotationAngle)
                .setDuration(200)
                .start()
        }
    }


    inner class Holder : EpoxyHolder() {
        var binding: ItemLanguageParentBinding? = null

        override fun bindView(itemView: View) {
            binding = DataBindingUtil.bind(itemView)
        }
    }
}