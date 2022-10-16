package com.example.data.session

import com.example.data.utils.preferences.SessionPreferenceKeys.KEY_LOGGED_IN
import com.example.data.utils.preferences.SessionPreferenceKeys.KEY_USER
import com.example.data.utils.preferences.SessionPreferences
import com.example.domain.models.User
import com.google.gson.Gson
import javax.inject.Inject

/**
 * Class to track session information like user login status
 */
class SessionManager @Inject constructor(private val preferences: SessionPreferences) {

    /** Tracks if user is logged in or not */
    var login: Boolean
        get() = preferences.getBoolean(KEY_LOGGED_IN, false)
        set(value) {
            preferences.putBoolean(KEY_LOGGED_IN, value)
        }

    /**
     * Tracks currently logged in user. If not logged in null is assigned.
     */
    var user: User?
        get() {
            val userJson = preferences.getStringOrNull(KEY_USER)
            val gson = Gson()
            return gson.fromJson(userJson, User::class.java)
        }
        set(newUser) {
            val gson = Gson()
            val userJson = gson.toJson(newUser)
            preferences.putString(KEY_USER, userJson)
        }
}