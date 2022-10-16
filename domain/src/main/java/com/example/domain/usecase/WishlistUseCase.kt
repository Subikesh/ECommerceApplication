package com.example.domain.usecase

import com.example.domain.models.Product
import com.example.domain.models.User
import com.example.domain.repository.WishlistRepository
import javax.inject.Inject

class WishlistUseCase @Inject constructor(private val wishlistRepository: WishlistRepository) {
    suspend fun addWishlist(user: User, product: Product) {
        wishlistRepository.addWishlist(user, product)
    }

    suspend fun removeWishlist(user: User, product: Product) {
        wishlistRepository.removeWishlist(user, product)
    }

    suspend fun getProductsInWishlist(user: User) : List<Product> {
        return wishlistRepository.getWishlist(user)
    }

    suspend fun isProductInWishlist(user: User, product: Product): Boolean {
        return wishlistRepository.findWishlistOrNull(user, product) != null
    }
}