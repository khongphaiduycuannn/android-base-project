package com.duycuannn.demo_base.ui.onboarding

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.duycuannn.base_project.components.BaseActivity
import com.duycuannn.base_project.utils.extensions.setPreventDoubleClick
import com.duycuannn.base_project.utils.showToast
import com.duycuannn.demo_base.R
import com.duycuannn.demo_base.databinding.ActivityOnboardingBinding
import com.duycuannn.demo_base.ui.onboarding.components.OnboardingAdapter
import com.duycuannn.demo_base.ui.onboarding.components.OnboardingItem
import kotlin.math.abs

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(
    ActivityOnboardingBinding::inflate
) {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, OnboardingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }


    private val onboardingItems = listOf(
        OnboardingItem(
            R.drawable.img_onboarding_1,
            R.string.onboarding_title_1,
            R.string.onboarding_description_1
        ),
        OnboardingItem(
            R.drawable.img_onboarding_2,
            R.string.onboarding_title_2,
            R.string.onboarding_description_2
        ),
        OnboardingItem(
            R.drawable.img_onboarding_3,
            R.string.onboarding_title_3,
            R.string.onboarding_description_3
        ),
        OnboardingItem(
            R.drawable.img_onboarding_4,
            R.string.onboarding_title_4,
            R.string.onboarding_description_4
        )
    )

    private val itemAdapter = OnboardingAdapter().apply {
        submitData(onboardingItems)
    }


    override fun initViews() {
        setupViewPager()
    }


    private fun setupViewPager() {
        binding.vpOnboardingItems.apply {
            adapter = itemAdapter
            setPageTransformation()
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    selectIndicator(position)
                    handleNextButtonClick(position)
                }
            })
        }
    }

    private fun selectIndicator(selectedIndex: Int) {
        binding.llIndicatorsContainer.children.forEachIndexed { index, view ->
            if (view is ImageView) {
                view.setImageResource(
                    if (index == selectedIndex) R.drawable.ic_onboarding_indicator_selected
                    else R.drawable.ic_onboarding_indicator
                )
            }
        }
    }

    private fun handleNextButtonClick(position: Int) {
        binding.tvNext.apply {
            if (position == itemAdapter.itemCount - 1) {
                text = getString(R.string.start)
                setPreventDoubleClick {
                    showToast("Start Main activity")
                }
            } else {
                text = getString(R.string.next)
                setPreventDoubleClick {
                    binding.vpOnboardingItems.currentItem += 1
                }
            }
        }
    }

    private fun ViewPager2.setPageTransformation() {
        clipToPadding = false
        clipChildren = false
        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(100))
        compositePageTransformer.addTransformer { view, position ->
            val r = 1 - abs(position)
            view.scaleY = 0.8f + r * 0.2f
            val absPosition = abs(position)
            view.alpha = 1.0f - 1.25f * absPosition
        }
        setPageTransformer(compositePageTransformer)
    }
}