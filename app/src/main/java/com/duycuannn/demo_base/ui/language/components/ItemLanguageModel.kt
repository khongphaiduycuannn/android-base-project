package com.duycuannn.demo_base.ui.language.components

import androidx.core.graphics.toColorInt
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.duycuannn.base_project.components.adapter.BaseEpoxyModel
import com.duycuannn.base_project.utils.extensions.setPreventDoubleClick
import com.duycuannn.demo_base.R
import com.duycuannn.demo_base.databinding.ItemLanguageBinding

@EpoxyModelClass
abstract class ItemLanguageModel : BaseEpoxyModel<ItemLanguageBinding>(
    ItemLanguageBinding::bind,
    R.layout.item_language
) {

    @EpoxyAttribute
    var language: LanguageItem? = null

    @EpoxyAttribute(hash = false)
    var onItemClickListener: ((iso: String) -> Unit)? = null

    override fun bind(binding: ItemLanguageBinding) {
        val language = language ?: return

        binding.tvLanguageName.text = language.name

        binding.rbLanguageSelected.isChecked = language.selected

        if (language.selected) {
            binding.tvLanguageName.setTextColor("#FD1BDF".toColorInt())
            binding.root.strokeColor = "#FD1BDF".toColorInt()
        } else {
            binding.tvLanguageName.setTextColor("#FFFFFF".toColorInt())
            binding.root.strokeColor = "#00000000".toColorInt()
        }

        binding.root.setPreventDoubleClick {
            onItemClickListener?.invoke(language?.iso ?: return@setPreventDoubleClick)
        }
    }
}