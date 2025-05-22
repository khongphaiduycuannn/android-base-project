package com.ndmquan.base_project.utils.extension

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigateToPage(
    id: Int,
    bundle: Bundle? = null,
    doOnError: ((Exception) -> Unit)? = null
) {
    try {
        findNavController().navigate(id, bundle)
    } catch (ex: Exception) {
        doOnError?.invoke(ex)
        ex.printStackTrace()
    }
}

fun Fragment.navigateUp(
    doOnError: ((Exception) -> Unit)? = null
) {
    try {
        findNavController().navigateUp()
    } catch (ex: Exception) {
        doOnError?.invoke(ex)
        ex.printStackTrace()
    }
}


fun <T> Fragment.setFragmentResult(key: String, result: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun <T> Fragment.observeFragmentResult(key: String, observer: (T) -> Unit) {
    val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle

    savedStateHandle?.getLiveData<T>(key)?.observe(viewLifecycleOwner) {
        observer(it)
    }
}

fun <T> Fragment.observeFragmentResultOnce(key: String, observer: (T) -> Unit) {
    val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle

    savedStateHandle?.getLiveData<T>(key)?.observe(viewLifecycleOwner) {
        if (it != null) {
            observer(it)
            savedStateHandle[key] = null
        }
    }
}