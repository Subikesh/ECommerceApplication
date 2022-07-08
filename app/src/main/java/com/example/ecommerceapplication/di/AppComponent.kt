package com.example.ecommerceapplication.di

import com.example.data.di.PreferenceModule
import com.example.data.di.RoomModule
import com.example.ecommerceapplication.ui.user.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PreferenceModule::class, AppModule::class, RoomModule::class])
interface AppComponent {
    fun inject(fragment: UserFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: SignupFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: WishlistFragment)
}