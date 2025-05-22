package com.ndmquan.base_project.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val loading: MutableLiveData<Boolean> = SingleLiveEvent()

    val isLoading: LiveData<Boolean>
        get() = loading


    protected fun <T> executeTask(
        request: suspend CoroutineScope.() -> T,
        onSuccess: (T) -> Unit,
        onError: (Exception) -> Unit = {},
        showLoading: Boolean = true
    ) {
        if (showLoading) showLoading()

        viewModelScope.launch {
            try {
                val response = request(this)
                onSuccess(response)
            } catch (ex: Exception) {
                ex.printStackTrace()
                onError(ex)
            } finally {
                if (showLoading) hideLoading()
            }
        }
    }

    protected fun <T> executeTaskWithState(
        request: suspend CoroutineScope.() -> DataState<T>,
        onSuccess: (T) -> Unit,
        onError: (Exception) -> Unit = {},
        showLoading: Boolean = true
    ) {
        if (showLoading) showLoading()

        viewModelScope.launch {
            when (val response = request(this)) {
                is DataState.Success -> {
                    onSuccess(response.data)
                    hideLoading()
                }

                is DataState.Error -> {
                    onError(response.exception)
                    hideLoading()
                }
            }
        }
    }


    fun showLoading() {
        loading.value = true
    }

    fun hideLoading() {
        loading.value = false
    }
}