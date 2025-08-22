package com.duycuannn.demo_base.ui.onboarding.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.duycuannn.demo_base.databinding.ItemOnboardingBinding

class OnboardingAdapter : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(
        private val binding: ItemOnboardingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(onboardingItem: OnboardingItem) {
            Glide.with(binding.root).load(onboardingItem.imageResId).into(binding.ivThumbnail)
            val title = binding.root.context.getString(onboardingItem.titleResId)
            val description = binding.root.context.getString(onboardingItem.descriptionResId)
            binding.tvTitle.text = title
            binding.tvDescription.text = description
        }
    }


    private val items = mutableListOf<OnboardingItem>()

    fun submitData(items: List<OnboardingItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnboardingViewHolder {
        val binding = ItemOnboardingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OnboardingViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}