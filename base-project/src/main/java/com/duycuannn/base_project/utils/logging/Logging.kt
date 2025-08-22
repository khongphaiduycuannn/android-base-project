package com.duycuannn.base_project.utils.logging

import android.util.Log
import com.duycuannn.base_project.BuildConfig

object Logging {

    private const val DEFAULT_TAG = "😵‍💫 thuongngoc 😒"

    private const val DEFAULT_BOX_WIDTH = 120


    private var showLogOnRelease = false

    fun showLogOnRelease() {
        showLogOnRelease = true
    }

    fun hideLogOnRelease() {
        showLogOnRelease = false
    }


    fun d(
        message: String,
        tag: String = DEFAULT_TAG
    ) {
        if (!showLogOnRelease && !BuildConfig.DEBUG) return
        Log.d(tag, formattedMessage(tag, message))
    }

    fun <T> d(
        collection: Collection<T>,
        tag: String = DEFAULT_TAG
    ) {
        if (!showLogOnRelease && !BuildConfig.DEBUG) return

        val message = StringBuilder()
        collection.forEachIndexed { index, it ->
            message.appendLine("$index. $it\n")
        }
        Log.d(tag, formattedMessage(tag, message.toString()))
    }

    fun e(
        message: String,
        tag: String = DEFAULT_TAG
    ) {
        if (!showLogOnRelease && !BuildConfig.DEBUG) return
        Log.e(tag, formattedMessage(tag, message))
    }

    fun <T> e(
        collection: Collection<T>,
        tag: String = DEFAULT_TAG
    ) {
        if (!showLogOnRelease && !BuildConfig.DEBUG) return

        val message = StringBuilder()
        collection.forEachIndexed { index, it ->
            message.appendLine("$index. $it\n")
        }
        Log.e(tag, formattedMessage(tag, message.toString()))
    }

    fun w(
        message: String,
        tag: String = DEFAULT_TAG
    ) {
        if (!showLogOnRelease && !BuildConfig.DEBUG) return
        Log.w(tag, formattedMessage(tag, message))
    }

    fun <T> w(
        collection: Collection<T>,
        tag: String = DEFAULT_TAG
    ) {
        if (!showLogOnRelease && !BuildConfig.DEBUG) return

        val message = StringBuilder()
        collection.forEachIndexed { index, it ->
            message.appendLine("$index. $it\n")
        }
        Log.w(tag, formattedMessage(tag, message.toString()))
    }

    fun i(
        message: String,
        tag: String = DEFAULT_TAG
    ) {
        if (!showLogOnRelease && !BuildConfig.DEBUG) return
        Log.i(tag, formattedMessage(tag, message))
    }

    fun <T> i(
        collection: Collection<T>,
        tag: String = DEFAULT_TAG
    ) {
        if (!showLogOnRelease && !BuildConfig.DEBUG) return

        val message = StringBuilder()
        collection.forEachIndexed { index, it ->
            message.appendLine("$index. $it\n")
        }
        Log.i(tag, formattedMessage(tag, message.toString()))
    }

    private fun formattedMessage(tag: String, message: String): String {
        val boxWidth = DEFAULT_BOX_WIDTH
        val contentWidth = boxWidth - 4
        val lines = mutableListOf<String>()
        var currentLine = ""

        message.split("\n").forEach {
            val words = it.split(" ")
            for (word in words) {
                if (word.length > contentWidth) {
                    if (currentLine.isNotEmpty()) {
                        lines.add(currentLine)
                        currentLine = ""
                    }

                    var remainingWord = word
                    while (remainingWord.length > contentWidth) {
                        lines.add(remainingWord.substring(0, contentWidth))
                        remainingWord = remainingWord.substring(contentWidth)
                    }
                    currentLine = remainingWord
                } else {
                    val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
                    if (testLine.length <= contentWidth) {
                        currentLine = testLine
                    } else {
                        lines.add(currentLine)
                        currentLine = word
                    }
                }
            }

            if (currentLine.isNotEmpty()) {
                lines.add(currentLine)
                currentLine = ""
            }
        }

        val result = StringBuilder()

        val headerDotLength = (boxWidth - tag.length) / 2 + 1
        result.appendLine(
            "  +" +
                    "-".repeat(headerDotLength)
                    + tag
                    + "-".repeat(headerDotLength)
                    + "+"
        )

        result.appendLine(" ".repeat(boxWidth))

        if (lines.size == 1) {
            val padding = boxWidth - 4 - lines[0].length
            val leftPad = padding / 2
            val rightPad = padding - leftPad
            result.appendLine(" ${" ".repeat(leftPad + 2)}${lines[0]}${" ".repeat(rightPad)} ")
        } else {
            for (line in lines) {
                val padding = boxWidth - 2 - line.length
                val leftPad = 2
                val rightPad = padding - leftPad
                result.appendLine(" $line${" ".repeat(rightPad)} ")
            }
        }

        result.appendLine(" ".repeat(boxWidth))

        result.append("+" + "-".repeat(boxWidth - 2) + "+")

        return result.toString()
    }
}