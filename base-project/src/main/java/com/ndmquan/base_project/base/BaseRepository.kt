package com.ndmquan.base_project.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {

    suspend fun <T> getResult(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        /* Use this try catch exactly error [HttpError, ParseJsonError, NetworkError ..] */
        transformError: (suspend (Exception) -> Exception)? = null,
        request: suspend CoroutineScope.() -> T,

        ): DataState<T> {
        return withContext(dispatcher) {
            try {
                DataState.Success(request())
            } catch (e: Exception) {
                DataState.Error(if (transformError != null) transformError(e) else e)
            }
        }
    }
}