package com.ndmquan.base.project

import android.app.Application
import com.ndmquan.base_project.utils.NetworkUtils

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkUtils.initialize(this)
    }
}