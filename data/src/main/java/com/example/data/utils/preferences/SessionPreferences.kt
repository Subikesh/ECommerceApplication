package com.example.data.utils.preferences

import android.content.SharedPreferences
import com.example.data.di.qualifiers.SessionPreference
import javax.inject.Inject

class SessionPreferences @Inject constructor(@SessionPreference sessionPreferences: SharedPreferences) :
    AppPreference(sessionPreferences)