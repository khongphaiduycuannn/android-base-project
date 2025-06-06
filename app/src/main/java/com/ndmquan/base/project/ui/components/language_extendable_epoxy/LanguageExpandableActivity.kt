package com.ndmquan.base.project.ui.components.language_extendable_epoxy

import androidx.recyclerview.widget.DefaultItemAnimator
import com.ndmquan.base.project.databinding.ActivityLanguageExpandableBinding
import com.ndmquan.base.project.ui.components.language_extendable_epoxy.adapter.LanguageListController
import com.ndmquan.base_project.base.activity.BaseActivity

class LanguageExpandableActivity : BaseActivity<ActivityLanguageExpandableBinding>(
    ActivityLanguageExpandableBinding::inflate
) {

    private val languageController = LanguageListController()


    override fun initViews() {
        setupRecyclerView()

        languageController.apply {
            submitData(LanguageData.languages)

            languageController.setOnLanguageClickListener {
                LanguageData.toggleLanguageExpansion(it)
                languageController.submitData(LanguageData.languages)

                // Invoke when language list expanded
            }

            languageController.setOnChildLanguageClickListener {
                LanguageData.selectChildLanguage(it)
                languageController.submitData(LanguageData.languages)

                // Invoke when language selected
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setController(languageController)
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            binding.recyclerView.itemAnimator = DefaultItemAnimator().apply {
                addDuration = 200
                removeDuration = 200
                changeDuration = 200
            }
        }
    }
}