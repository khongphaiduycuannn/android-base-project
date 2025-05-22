package com.ndmquan.base_project.utils.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectIn(coroutineScope: CoroutineScope, collector: suspend (T) -> Unit) {
    coroutineScope.launch {
        collect { collector(it) }
    }
}