package com.example.data.repository

import com.example.data.mapper.ProductEntityMapperImpl
import com.example.data.roomdb.dao.ProductDao
import com.example.data.roomdb.dao.WishlistDao
import com.example.data.roomdb.entities.UserProductCrossRef
import com.example.domain.models.Product
import com.example.domain.models.User
import com.example.domain.repository.WishlistRepository
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val wishlistDao: WishlistDao
) : WishlistRepository {
    /** Add product as wishlist for the user */
    override suspend fun addWishlist(user: User, product: Product) {
        productDao.insert(ProductEntityMapperImpl.toEntity(product))
        wishlistDao.addWishlist(UserProductCrossRef(user.userId, product.productId))
    }

    override suspend fun removeWishlist(user: User, product: Product) {
        wishlistDao.removeWishlist(UserProductCrossRef(user.userId, product.productId))
    }

    /** Get the list of products saved in user's wishlist */
    override suspend fun getWishlist(user: User): List<Product> {
        val products = wishlistDao.getWishlist(user.userId).products

        val productModels = mutableListOf<Product>()
        for (product in products) productModels.add(ProductEntityMapperImpl.fromEntity(product))
        return productModels
    }

    /** Check if this product is already in user's wishlist */
    override suspend fun isInWishlist(user: User, product: Product): Boolean {
        val userProductRef = wishlistDao.findWishlist(user.userId, product.productId)
        return userProductRef != null
    }
}