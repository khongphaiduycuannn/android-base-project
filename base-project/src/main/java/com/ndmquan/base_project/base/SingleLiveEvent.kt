package com.ndmquan.base_project.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val hasBeenHandled = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) { value ->
            if (hasBeenHandled.compareAndSet(false, true)) {
                observer.onChanged(value)
            }
        }
    }

    override fun setValue(value: T?) {
        hasBeenHandled.set(false)
        super.setValue(value)
    }
}