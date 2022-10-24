package com.example.ecommerceapplication.di.module

import com.example.data.repository.ecom.ProductsRepositoryImpl
import com.example.data.repository.WishlistRepositoryImpl
import com.example.data.repository.ecom.CategoryRepositoryImpl
import com.example.domain.repository.CategoryRepository
import com.example.domain.repository.ProductsRepository
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

    @Binds
    fun bindProductsRepository(productsRepository: ProductsRepositoryImpl): ProductsRepository

    @Binds
    fun bindCategoryRepository(categoryRepository: CategoryRepositoryImpl): CategoryRepository
}