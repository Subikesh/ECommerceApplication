package com.example.data.utils.preferences

import android.content.SharedPreferences
import androidx.core.content.edit

abstract class AppPreference(private val preference: SharedPreferences) {

    fun putString(key: String, value: String) {
        preference.edit {
            putString(key, value)
        }
    }

    fun putInt(key: String, value: Int) {
        preference.edit {
            putInt(key, value)
        }
    }

    fun putBoolean(key: String, value: Boolean) {
        preference.edit {
            putBoolean(key, value)
        }
    }

    fun putLong(key: String, value: Long) {
        preference.edit {
            putLong(key, value)
        }
    }

    fun putFloat(key: String, value: Float) {
        preference.edit {
            putFloat(key, value)
        }
    }

    fun getString(key: String, default: String = ""): String {
        return preference.getString(key, default)!!
    }

    fun getStringOrNull(key: String): String? {
        return preference.getString(key, null)
    }


    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return preference.getBoolean(key, default)
    }

    fun getInt(key: String, default: Int = -1): Int {
        return preference.getInt(key, default)
    }

    fun getLong(key: String, default: Long = 0L): Long {
        return preference.getLong(key, default)
    }

    fun getFloat(key: String, default: Float = 0.0F): Float {
        return preference.getFloat(key, default)
    }

    fun clear() {
        preference.edit {
            clear()
            commit()
        }
    }

    fun removePref(key: String) {
        preference.edit {
            remove(key)
            commit()
        }
    }

    fun removePref(removePrefBlock : ()->Unit){
        preference.edit {
            removePrefBlock()
            commit()
        }
    }

    fun putAll(values: Map<String, Any>) {
        preference.edit {
            values.forEach { (key, value) ->
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Boolean -> putBoolean(key, value)
                    is Long -> putLong(key, value)
                    is Float -> putFloat(key, value)
                    else -> throw IllegalStateException("Value type unknown")
                }
            }
        }
    }

    fun remove(keys: List<String>) {
        preference.edit {
            keys.forEach { key ->
                remove(key)
            }
            commit()
        }
    }

}