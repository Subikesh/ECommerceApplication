package com.example.ecommerceapplication.di.module

import android.content.Context
import android.content.SharedPreferences
import com.example.data.di.qualifiers.SessionPreference
import com.example.data.utils.preferences.PreferenceUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    @SessionPreference
    @Singleton
    fun provideSessionPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PreferenceUtils.SESSION_PREFERENCE, Context.MODE_PRIVATE)
}