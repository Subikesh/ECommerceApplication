package com.example.ecommerceapplication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceapplication.ui.user.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

//@Module
//abstract class ViewModelModule {
//
//    @Binds
//    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(UserViewModel::class)
//    internal abstract fun postUserViewModel(viewModel: UserViewModel): ViewModel
//
//}