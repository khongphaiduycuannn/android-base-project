package com.ndmquan.base_project.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import com.ndmquan.base_project.utils.NetworkUtils._isNetworkConnected
import com.ndmquan.base_project.utils.extension.collectIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.net.HttpURLConnection
import java.net.URL


private const val PINGING_TIMEOUT = 1500

val isNetworkConnected get() = _isNetworkConnected

/**
 * Utility object for monitoring actual internet connectivity status.
 * Uses Android's network callbacks combined with active pinging to verify internet access.
 */
object NetworkUtils {

    private var connectivityManager: ConnectivityManager? = null


    /**
     * Variable representing current internet connectivity.
     * This value is automatically updated by observing the [isNetworkConnected] Flow.
     * This value initialized when initialize() function is first called.
     */
    var _isNetworkConnected = false
        private set


    /**
     * Call this method once during app startup to initialize the network monitor.
     * It begins collecting from [isNetworkConnected] Flow to keep [_isNetworkConnected] updated.
     *
     * @param context Use application context to avoid memory leaks.
     */
    fun initialize(context: Context) {
        if (connectivityManager != null) return
        connectivityManager = context.getSystemService<ConnectivityManager>()

        isNetworkConnected.stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        ).collectIn(CoroutineScope(Dispatchers.IO)) {
            _isNetworkConnected = it
        }
    }


    /**
     * A [Flow] that emits the actual internet connection status.
     */
    @OptIn(FlowPreview::class)
    val isNetworkConnected: Flow<Boolean>
        get() = callbackFlow {
            val cm = connectivityManager
            if (cm == null) {
                close()
                return@callbackFlow
            }

            var lastPingJob: Job? = null

            val callback = object : ConnectivityManager.NetworkCallback() {

                fun pingAndEmit() {
                    lastPingJob?.cancel()
                    lastPingJob = launch {
                        val result = pingGoogle()
                        trySend(result)
                    }
                }

                override fun onCapabilitiesChanged(
                    network: Network, networkCapabilities: NetworkCapabilities
                ) = pingAndEmit()

                override fun onUnavailable() {
                    trySend(false)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(false)
                }

                override fun onAvailable(network: Network) = pingAndEmit()
            }

            cm.registerDefaultNetworkCallback(callback)

            awaitClose {
                connectivityManager?.unregisterNetworkCallback(callback)
                lastPingJob?.cancel()
            }
        }.debounce(PINGING_TIMEOUT.toLong())
            .conflate()
            .distinctUntilChanged()
}

/**
 * Performs a HTTP GET request to Google's homepage
 * to verify real internet access.
 *
 * @return true if the HTTP status code is 200, false otherwise.
 */
private suspend fun pingGoogle(): Boolean = withContext(Dispatchers.IO) {
    withTimeoutOrNull(PINGING_TIMEOUT.toLong()) {
        try {
            val url = URL("https://www.google.com")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = PINGING_TIMEOUT
            connection.readTimeout = PINGING_TIMEOUT
            connection.requestMethod = "GET"
            connection.connect()
            val responseCode = connection.responseCode
            connection.disconnect()
            responseCode == 200
        } catch (e: Exception) {
            false
        }
    } ?: false
}
