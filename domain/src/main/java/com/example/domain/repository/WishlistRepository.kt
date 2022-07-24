package com.example.domain.repository

import com.example.domain.models.Product
import com.example.domain.models.User

interface WishlistRepository {
    suspend fun addWishlist(user: User, product: Product)

    suspend fun removeWishlist(user: User, product: Product)

    suspend fun getWishlist(user: User): List<Product>

    suspend fun isInWishlist(user: User, product: Product): Boolean
}