package com.ndmquan.base_project.utils.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.observeNotNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner) {
        it?.let(observer)
    }
}

fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner) {
        it?.let(observer)
    }
}