package com.duycuannn.base_project.utils.extensions

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val DEBOUNCE_TIME = 300L

const val SCALE_DOWN = 0.85f
const val SCALE_NORMAL = 1.0f
const val ANIMATION_DURATION = 150L


var lastClickTime: Long = 0


fun View.setPreventDoubleClick(debounceTime: Long = DEBOUNCE_TIME, action: (view: View) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View?) {
            if (System.currentTimeMillis() - lastClickTime < debounceTime) return
            v?.let { action.invoke(it) }
            lastClickTime = System.currentTimeMillis()
        }
    })
}

@SuppressLint("ClickableViewAccessibility")
fun View.setOnClickWithScaleAnimation(
    debounceTime: Long = DEBOUNCE_TIME,
    scaleDown: Float = SCALE_DOWN,
    animationDuration: Long = ANIMATION_DURATION,
    action: (view: View) -> Unit
) {
    this.setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.animate()
                    .scaleX(scaleDown)
                    .scaleY(scaleDown)
                    .setDuration(animationDuration)
                    .start()
            }

            MotionEvent.ACTION_UP -> {
                v.animate()
                    .scaleX(SCALE_NORMAL)
                    .scaleY(SCALE_NORMAL)
                    .setDuration(animationDuration)
                    .start()

                if (System.currentTimeMillis() - lastClickTime >= debounceTime) {
                    action.invoke(v)
                    lastClickTime = System.currentTimeMillis()
                }
            }

            MotionEvent.ACTION_CANCEL -> {
                v.animate()
                    .scaleX(SCALE_NORMAL)
                    .scaleY(SCALE_NORMAL)
                    .setDuration(animationDuration)
                    .start()
            }
        }
        true
    }
}


fun View.setOnLongPressListener(
    longPressDuration: Long = 500L,
    onPressStart: (() -> Unit)? = null,
    onPressEnd: ((wasLongPress: Boolean, duration: Long) -> Unit)? = null,
    onPressCancel: (() -> Unit)? = null,
    onLongPress: (() -> Unit)? = null
) {
    var longPressJob: Job? = null
    var isLongPressed = false
    var pressStartTime = 0L
    val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    this.setOnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isLongPressed = false
                pressStartTime = System.currentTimeMillis()

                longPressJob = scope.launch {
                    delay(longPressDuration)
                    isLongPressed = true
                    onLongPress?.invoke()
                }

                onPressStart?.invoke()
            }

            MotionEvent.ACTION_UP -> {
                longPressJob?.cancel()
                val duration = System.currentTimeMillis() - pressStartTime
                val distance = System.currentTimeMillis() - lastClickTime
                onPressEnd?.invoke(isLongPressed, duration)

                if (!isLongPressed && distance > DEBOUNCE_TIME) {
                    view.performClick()
                    lastClickTime = System.currentTimeMillis()
                }
            }

            MotionEvent.ACTION_CANCEL -> {
                longPressJob?.cancel()
                onPressCancel?.invoke()
            }
        }
        onTouchEvent(event)
    }
}