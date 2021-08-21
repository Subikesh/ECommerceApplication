package com.example.domain.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(val _context: Context) {
    var preferences: SharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = preferences.edit()

    companion object {
        private val PREF_NAME: String = "EcommerceSession"
        private val KEY_LOGGED_IN: String = "isLoggedin"
    }

    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_LOGGED_IN, isLoggedIn)
        editor.commit()
    }

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(KEY_LOGGED_IN, false)
    }
}