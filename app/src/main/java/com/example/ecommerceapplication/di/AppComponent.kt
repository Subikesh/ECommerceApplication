package com.example.ecommerceapplication.di

import com.example.data.di.PreferenceModule
import com.example.data.di.RoomModule
import com.example.ecommerceapplication.ui.user.LoginFragment
import com.example.ecommerceapplication.ui.user.UserFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PreferenceModule::class, AppModule::class, RoomModule::class])
interface AppComponent {
    fun inject(activity: UserFragment)
    fun inject(activity: LoginFragment)
}