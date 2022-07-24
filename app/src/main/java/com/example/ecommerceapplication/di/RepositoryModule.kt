package com.example.ecommerceapplication.di

import com.example.data.repository.WishlistRepositoryImpl
import com.example.domain.repository.WishlistRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindWishlistRepository(wishlistRepository: WishlistRepositoryImpl): WishlistRepository
}