package com.duycuannn.demo_base.ui.language

import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.duycuannn.base_project.components.BaseActivity
import com.duycuannn.base_project.utils.extensions.setPreventDoubleClick
import com.duycuannn.base_project.utils.showToast
import com.duycuannn.demo_base.databinding.ActivityLanguageBinding
import com.duycuannn.demo_base.ui.language.components.itemLanguage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LanguageActivity : BaseActivity<ActivityLanguageBinding>(
    ActivityLanguageBinding::inflate
) {

    private val viewModel by viewModels<LanguageViewModel>()


    override fun initViews() {
        binding.rvLanguages.withModels {
            val languages = viewModel.displayLanguages.value
            languages.forEach {
                itemLanguage {
                    id(it.iso)
                    language(it)
                    onItemClickListener { _ -> viewModel.selectLanguage(it.iso) }
                }
            }
        }
    }

    override fun handleEvents() {
        binding.ivConfirm.setPreventDoubleClick {
            viewModel.applyLanguage()
            showToast("Language applied successfully")
        }
    }

    override fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.displayLanguages.collect { binding.rvLanguages.requestModelBuild() } }
            }
        }
    }
}