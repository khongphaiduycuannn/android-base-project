package com.duycuannn.demo_base

import com.duycuannn.base_project.components.BaseActivity
import com.duycuannn.demo_base.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    data class Item(
        val text: String,
        val checked: Boolean = false
    )

    private val data = mutableListOf(
        Item("Item 1"),
        Item("Item 2"),
        Item("Item 3"),
        Item("Item 4"),
        Item("Item 5"),
        Item("Item 6"),
        Item("Item 7"),
        Item("Item 8"),
        Item("Item 9"),
        Item("Item 10"),
        Item("Item 11"),
        Item("Item 12"),
        Item("Item 13"),
        Item("Item 14"),
        Item("Item 15"),
        Item("Item 16"),
        Item("Item 17"),
        Item("Item 18"),
        Item("Item 19"),
        Item("Item 20"),
        Item("Item 21"),
        Item("Item 22"),
        Item("Item 23"),
        Item("Item 24"),
        Item("Item 25"),
        Item("Item 26"),
        Item("Item 27"),
        Item("Item 28"),
        Item("Item 29"),
        Item("Item 30"),
        Item("Item 31"),
        Item("Item 32"),
        Item("Item 33"),
        Item("Item 34"),
        Item("Item 35"),
        Item("Item 36"),
        Item("Item 37"),
        Item("Item 38"),
        Item("Item 39"),
        Item("Item 40"),
        Item("Item 41"),
        Item("Item 42"),
        Item("Item 43"),
        Item("Item 44"),
        Item("Item 45"),
        Item("Item 46"),
        Item("Item 47"),
        Item("Item 48"),
        Item("Item 49"),
        Item("Item 50")
    )


    override fun handleEvents() {
        binding.rv.withModels {
            data.forEachIndexed { index, item ->
                itemTest {
                    id(index)
                    text(item.text)
                    checked(item.checked)
                    onClick {
                        data[index] = data[index].copy(checked = !data[index].checked)
                        binding.rv.requestModelBuild()
                    }
                }
            }
        }
    }
}