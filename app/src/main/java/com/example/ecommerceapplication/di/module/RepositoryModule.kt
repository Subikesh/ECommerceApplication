package com.example.ecommerceapplication.di.module

import com.example.data.repository.WishlistRepositoryImpl
import com.example.domain.repository.WishlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindWishlistRepository(withlistRepositoryImpl: WishlistRepositoryImpl): WishlistRepository
}