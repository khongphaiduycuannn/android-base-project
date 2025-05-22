package com.ndmquan.base_project.utils

import android.graphics.Color
import java.util.Locale

private const val ERROR_COLOR = "#E53935"

fun String.parseColor(): Int {
    return try {
        Color.parseColor(this)
    } catch (e: IllegalArgumentException) {
        Color.parseColor(ERROR_COLOR)
    }
}

fun Int.hexString(withAlpha: Boolean = false): String {
    return try {
        val a = Color.alpha(this)
        val r = Color.red(this)
        val g = Color.green(this)
        val b = Color.blue(this)

        if (withAlpha) {
            String.format(Locale.US, "#%02X%02X%02X%02X", a, r, g, b)
        } else {
            String.format(Locale.US, "#%02X%02X%02X", r, g, b)
        }
    } catch (e: IllegalArgumentException) {
        ERROR_COLOR
    }
}

fun Int.alpha(): Float {
    return Color.alpha(this) / 255f
}

fun Int.rgb(): Int {
    val r = Color.red(this)
    val g = Color.green(this)
    val b = Color.blue(this)
    return String.format(Locale.US, "#%02X%02X%02X", r, g, b).parseColor()
}

fun Int.argb(alpha: Float): Int {
    val a = (alpha * 255).toInt()
    val r = Color.red(this)
    val g = Color.green(this)
    val b = Color.blue(this)
    val hexString = String.format(Locale.US, "#%02X%02X%02X%02X", a, r, g, b)
    return hexString.parseColor()
}

