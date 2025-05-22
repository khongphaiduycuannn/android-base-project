package com.ndmquan.base_project.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.tencent.mmkv.MMKV

object LocalStorageUtils {

    fun defaultPrefs(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun defaultMMKV(context: Context): MMKV =
        MMKV.initialize(context).run { MMKV.defaultMMKV() }
}


/**
 * Getter for SharedPreferences.
 *
 * Retrieves a value of type [T] for the given [key].
 * If the key doesn't exist, [defaultValue] will be returned (or inferred default if not provided).
 *
 * Supported types:
 * - String
 * - Int
 * - Boolean
 * - Float
 * - Long
 *
 * @param key The name of the preference to retrieve
 * @param defaultValue The value to return if the preference does not exist
 * @return The stored value or default
 *
 * @throws UnsupportedOperationException if the type [T] is not supported
 */
inline operator fun <reified T : Any> SharedPreferences.get(
    key: String,
    defaultValue: T? = null
): T? {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        else -> throw UnsupportedOperationException("Type ${T::class} is not supported.")
    }
}

/**
 * Setter for SharedPreferences.
 *
 * Stores a value for the given [key], or updates it if it already exists.
 *
 * Supported types:
 * - String
 * - Int
 * - Boolean
 * - Float
 * - Long
 *
 * @param key The name of the preference to modify
 * @param value The value to store
 *
 * @throws UnsupportedOperationException if the value type is not supported
 */
operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Type ${value?.javaClass} is not supported")
    }
}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}


/**
 * Getter for MMKV.
 *
 * Retrieves a value of type [T] for the given [key].
 * If the key doesn't exist, [defaultValue] will be returned (or inferred default if not provided).
 *
 * Supported types:
 * - String
 * - Int
 * - Boolean
 * - Float
 * - Long
 *
 * @param key The name of the preference to retrieve
 * @param defaultValue The value to return if the preference does not exist
 * @return The stored value or default
 *
 * @throws UnsupportedOperationException if the type [T] is not supported
 */
inline operator fun <reified T : Any> MMKV.get(key: String, defaultValue: T? = null): T? {
    return when (T::class) {
        String::class -> decodeString(key, defaultValue as? String) as T?
        Int::class -> decodeInt(key, defaultValue as? Int ?: -1) as T
        Boolean::class -> decodeBool(key, defaultValue as? Boolean ?: false) as T
        Float::class -> decodeFloat(key, defaultValue as? Float ?: -1f) as T
        Long::class -> decodeLong(key, defaultValue as? Long ?: -1L) as T
        Double::class -> decodeDouble(key, defaultValue as? Double ?: -1.0) as T
        else -> throw UnsupportedOperationException("Type ${T::class} is not supported")
    }
}

/**
 * Setter for MMKV.
 *
 * Stores a value for the given [key], or updates it if it already exists.
 *
 * Supported types:
 * - String
 * - Int
 * - Boolean
 * - Float
 * - Long
 *
 * @param key The name of the preference to modify
 * @param value The value to store
 *
 * @throws UnsupportedOperationException if the value type is not supported
 */
operator fun MMKV.set(key: String, value: Any?) {
    when (value) {
        is String? -> encode(key, value)
        is Int -> encode(key, value)
        is Boolean -> encode(key, value)
        is Float -> encode(key, value)
        is Long -> encode(key, value)
        is Double -> encode(key, value)
        else -> throw UnsupportedOperationException("Type ${value?.javaClass} is not supported")
    }
}
