package com.example.data.usecases

import android.content.Context
import android.util.Log
import com.example.data.repository.ProductEntityMapperImpl
import com.example.data.roomdb.DatabaseContract
import com.example.data.roomdb.entities.UserProductCrossRef
import com.example.domain.models.Product
import com.example.domain.models.User

class UserWishlist(context: Context) {
    /** Database object to access database */
    private val db = DatabaseContract.getInstance(context)

    /** Add product as wishlist for the user */
    suspend fun addWishlist(user: User, product: Product) {
        db.wishlistDao().addWishlist(UserProductCrossRef(user.userId, product.productId))
    }

    suspend fun removeWishlist(user: User, product: Product) {
        db.wishlistDao().removeWishlist(UserProductCrossRef(user.userId, product.productId))
    }

    /** Get the list of products saved in user's wishlist */
    suspend fun getWishlist(user: User): List<Product> {
        val products = db.wishlistDao().getWishlist(user.userId).products

        Log.d("Wishlist", "Wishlisted products: ${products})")
        val productModels = mutableListOf<Product>()
        for (product in products) productModels.add(ProductEntityMapperImpl.fromEntity(product))
        Log.d("Wishlist", productModels.toString())
        return productModels
    }

    /** Check if this product is already in user's wishlist */
    suspend fun isInWishlist(user: User, product: Product): Boolean {
        val userProductRef = db.wishlistDao().findWishlist(user.userId, product.productId)
         return userProductRef != null
    }
}