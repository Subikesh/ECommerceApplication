package com.example.data.session

import android.content.Context
import android.content.SharedPreferences
import com.example.data.entities.User
import com.google.gson.Gson

/**
 * Class to track session information like user login status
 */
class SessionManager(context: Context) {
    var preferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = preferences.edit()

    /** Tracks if user is logged in or not */
    var login: Boolean
        get() = preferences.getBoolean(KEY_LOGGED_IN, false)
        set(value) {
            editor.putBoolean(KEY_LOGGED_IN, value)
            editor.commit()
        }

    /**
     * Tracks currently logged in user. If not logged in null is assigned.
     */
    var user: User?
        get() {
            val userJson = preferences.getString(KEY_USER, null)
            val gson = Gson()
            return gson.fromJson(userJson, User::class.java)
        }
        set(newUser) {
            val gson = Gson()
            val userJson = gson.toJson(newUser)
            editor.putString(KEY_USER, userJson)
            editor.commit()
        }

    companion object {
        private const val PREF_NAME: String = "EcommerceSession"
        private const val KEY_LOGGED_IN: String = "isLoggedIn"
        private const val KEY_USER: String = "currentUser"
    }
}