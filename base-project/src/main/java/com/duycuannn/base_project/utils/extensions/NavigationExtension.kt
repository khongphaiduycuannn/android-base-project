package com.duycuannn.base_project.utils.extensions

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.duycuannn.base_project.utils.logging.Logging

fun Fragment.safeNavigateTo(
    id: Int,
    bundle: Bundle? = null,
    navOptions: NavOptions? = null,
    onError: ((Exception) -> Unit)? = null
): Boolean {
    return safeAction(onError) {
        if (!isFragmentSafe()) throw NavigateFailedException()
        findNavController().navigate(id, bundle, navOptions)
    }
}

fun Fragment.safeNavigateTo(
    directions: NavDirections,
    navOptions: NavOptions? = null,
    onError: ((Exception) -> Unit)? = null
): Boolean {
    return safeAction(onError) {
        if (!isFragmentSafe()) throw NavigateFailedException()
        findNavController().navigate(directions, navOptions)
    }
}

fun Fragment.safeNavigateUp(
    onError: ((Exception) -> Unit)? = null
): Boolean {
    return safeAction(onError) {
        if (!isFragmentSafe()) throw NavigateFailedException()
        findNavController().navigateUp()
    }
}

fun Activity.safeNavigateTo(
    containerId: Int,
    directions: NavDirections,
    navOptions: NavOptions? = null,
    onError: ((Exception) -> Unit)? = null
): Boolean {
    return safeAction(onError) {
        findNavController(containerId).navigate(directions, navOptions)
    }
}


class NavigateFailedException :
    IllegalStateException("Fragment is not in valid state for navigation")

private fun safeAction(
    onError: ((Exception) -> Unit)? = null,
    action: () -> Unit
): Boolean {
    return try {
        action()
        true
    } catch (ex: Exception) {
        Logging.e("SafeAction: ${ex.message}")
        onError?.invoke(ex)
        false
    }
}

private fun Fragment.isFragmentSafe(): Boolean {
    return isAdded &&
            activity != null &&
            !isDetached &&
            !isRemoving &&
            view != null
}


