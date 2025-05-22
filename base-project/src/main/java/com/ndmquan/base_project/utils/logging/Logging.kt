package com.ndmquan.base_project.utils.logging

import android.util.Log
import com.ndmquan.base_project.BuildConfig

object Logging {

    private const val DEFAULT_LEVEL = Log.ERROR

    private const val DEFAULT_TAG = "😵‍💫 thuongngoc 😒"


    private var showLogOnRelease = false

    fun showLogOnRelease() {
        showLogOnRelease = true
    }

    fun hideLogOnRelease() {
        showLogOnRelease = false
    }


    fun d(
        tag: String = DEFAULT_TAG,
        message: String
    ) {
        if (!showLogOnRelease && !BuildConfig.DEBUG) return
        Log.d(tag, message)
    }

    fun e(
        tag: String = DEFAULT_TAG,
        message: String
    ) {
        if (!showLogOnRelease && !BuildConfig.DEBUG) return
        Log.e(tag, message)
    }

    fun w(
        tag: String = DEFAULT_TAG,
        message: String
    ) {
        if (!showLogOnRelease && !BuildConfig.DEBUG) return
        Log.w(tag, message)
    }

    fun i(
        tag: String = DEFAULT_TAG,
        message: String
    ) {
        if (!showLogOnRelease && !BuildConfig.DEBUG) return
        Log.i(tag, message)
    }

    private fun formattedMessage(tag: String, message: String) {
        val stringBuilder = StringBuilder()
        val header =
            "__________________________________________________${tag}__________________________________________________\n"

        stringBuilder.append(header)
        val tagLength = tag.length
        val headerLength = header.length
        val lineLength = headerLength - 2 - tagLength

        (1..headerLength).forEachIndexed { index, _ ->
            if (index == 1 || index == headerLength) stringBuilder.append("|")
            else stringBuilder.append(" ")
        }
    }
}