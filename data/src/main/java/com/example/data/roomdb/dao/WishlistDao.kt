package com.example.data.roomdb.dao

import androidx.room.*
import com.example.data.roomdb.entities.UserProductCrossRef
import com.example.data.roomdb.relations.UserWithProducts

@Dao
interface WishlistDao {
    /** Get list of products wishlisted by user */
    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getWishlist(userId: Int): UserWithProducts

    /** Insert a wishlist item to a user */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWishlist(userToProduct: UserProductCrossRef)

    /** Remove an item from wishlist */
    @Delete
    fun removeWishlist(userToProduct: UserProductCrossRef)

    /** Check if this product is already in user's wishlist */
    @Query("SELECT * FROM userproductcrossref WHERE userId = :userId AND productId = :productId")
    fun findWishlist(userId: Int, productId: String): UserProductCrossRef?
}