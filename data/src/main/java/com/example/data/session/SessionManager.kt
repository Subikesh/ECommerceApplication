package com.example.data.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(val _context: Context) {
    var preferences: SharedPreferences =
        _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = preferences.edit()
    var login: Boolean
        get() = preferences.getBoolean(KEY_LOGGED_IN, false)
        set(value) {
            editor.putBoolean(KEY_LOGGED_IN, value)
            editor.commit()
        }

    companion object {
        private val PREF_NAME: String = "EcommerceSession"
        private val KEY_LOGGED_IN: String = "isLoggedin"
    }
}