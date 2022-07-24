package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.data.di.qualifiers.SessionPreference
import com.example.data.utils.preferences.PreferenceUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferenceModule {

    @Provides
    @SessionPreference
    @Singleton
    fun provideSessionPreference(context: Context): SharedPreferences =
        context.getSharedPreferences(PreferenceUtils.SESSION_PREFERENCE, Context.MODE_PRIVATE)
}