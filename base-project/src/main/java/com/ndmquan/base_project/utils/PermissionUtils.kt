package com.ndmquan.base_project.utils

import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.util.UUID

/**
 * Checks if a specific permission is granted for the activity.
 *
 * @param permission The permission to check (e.g., android.permission.CAMERA).
 * @return True if the permission is granted, false otherwise.
 */
fun AppCompatActivity.isPermissionGranted(permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(this, permission) == 0
}


/**
 * Determines if a rationale should be shown for requesting a permission.
 *
 * @param permission The permission to check (e.g., android.permission.CAMERA).
 * @return True if the rationale should be shown, false otherwise.
 */
fun AppCompatActivity.shouldShowPermissionRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}

/**
 * Opens the application settings screen to allow the user to manage permissions.
 */
fun AppCompatActivity.openSettingPermission() {
    val intent = Intent("android.settings.APPLICATION_DETAILS_SETTINGS")
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}


/**
 * A utility to handle permission requests in an Android application.
 *
 * This class provides methods to request single or multiple permissions and handle the results
 *
 * @param activity The [AppCompatActivity] instance used to request permissions.
 */
class PermissionUtils(
    private val activity: AppCompatActivity
) {

    interface PermissionCallback {
        fun onAllGranted(permission: List<String>)
        fun onDenied(listGranted: List<String>, listDenied: List<String>)
        fun onShouldOpenSetting(openSettingAction: () -> Unit)
    }


    private var permissions: List<String> = listOf()

    private val isPermissionGranted: Boolean
        get() = permissions.all { activity.isPermissionGranted(it) }

    private val isShouldShowPermissionRationale: Boolean
        get() = permissions.any { activity.shouldShowPermissionRationale(it) }


    /**
     * Requests a single permission and handles the result via a callback.
     *
     * @param permission The permission to request (e.g., android.permission.CAMERA).
     * @param callback The [PermissionCallback] to handle the permission result.
     */
    fun requestPermission(
        permission: String,
        callback: PermissionCallback,
    ) {
        requestPermissions(
            listOf(permission),
            callback
        )
    }


    /**
     * Requests multiple permissions and handles the result via a callback.
     *
     * If all permissions are already granted, the [PermissionCallback.onAllGranted] callback is triggered immediately.
     * Otherwise, a permission request is launched, and the result is processed to determine granted and denied permissions.
     * If any permissions are permanently denied, the [PermissionCallback.onShouldOpenSetting] callback is triggered.
     *
     * @param permissions The list of permissions to request (e.g., listOf(android.permission.CAMERA, android.permission.WRITE_EXTERNAL_STORAGE)).
     * @param callback The [PermissionCallback] to handle the permission result.
     */
    fun requestPermissions(
        permissions: List<String>,
        callback: PermissionCallback
    ) {
        this.permissions = permissions

        if (isGrantAll()) {
            callback.onAllGranted(permissions)
            return
        }

        val contract = ActivityResultContracts.RequestMultiplePermissions()
        activity.activityResultRegistry
            .register("requestPermission_${UUID.randomUUID()}", contract) { newResult ->
                if (isGrantAll()) {
                    callback.onAllGranted(permissions)
                    return@register
                }

                val granted = newResult.filter { it.value }.keys.toList()
                val denied = newResult.filter { !it.value }.keys.toList()

                callback.onDenied(granted, denied)

                if (!isShouldShowPermissionRationale) {
                    callback.onShouldOpenSetting(openSettingAction = {
                        activity.openSettingPermission()
                    })
                }
            }
            .launch(permissions.toTypedArray())
    }


    private fun isGrantAll(): Boolean {
        return isPermissionGranted
    }
}