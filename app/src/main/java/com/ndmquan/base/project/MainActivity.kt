package com.ndmquan.base.project

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.ndmquan.base.project.databinding.ActivityMainBinding
import com.ndmquan.base_project.base.activity.BaseActivity
import com.ndmquan.base_project.utils.NetworkUtils
import com.ndmquan.base_project.utils.extension.collectIn

class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    override fun observeData() {
        NetworkUtils
            .isNetworkConnected
            .collectIn(lifecycleScope) {
                Log.d(TAG, "observeData: ")
            }
    }
}