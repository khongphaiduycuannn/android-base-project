package com.duycuannn.demo_base

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.duycuannn.base_project.components.adapter.BaseEpoxyModel
import com.duycuannn.demo_base.databinding.ItemTestBinding

@EpoxyModelClass
abstract class ItemTestModel() : BaseEpoxyModel<ItemTestBinding>(
    binder = ItemTestBinding::bind,
    defaultLayout = R.layout.item_test
) {

    @EpoxyAttribute
    var text: String = ""

    @EpoxyAttribute
    var checked: Boolean = false

    @EpoxyAttribute(hash = false)
    var onClick: () -> Unit = {}


    override fun bind(binding: ItemTestBinding) {
        binding.radioButton.isChecked = checked
        binding.textView.text = text
        binding.root.setOnClickListener {
            onClick()
        }
    }
}