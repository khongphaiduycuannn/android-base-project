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
        binding.recyclerView.setController(languageController)
        binding.recyclerView.itemAnimator = DefaultItemAnimator().apply {
            addDuration = 200
            removeDuration = 200
            changeDuration = 200
        }

        languageController.submitData(LanguageData.languages)

        languageController.setOnLanguageClickListener {
            // update UI
            LanguageData.toggleLanguageExpansion(it)
            languageController.submitData(LanguageData.languages)

            // set locale...
        }

        languageController.setOnChildLanguageClickListener {
            // update UI
            LanguageData.selectChildLanguage(it)
            languageController.submitData(LanguageData.languages)

            // set locale...
        }
    }
}