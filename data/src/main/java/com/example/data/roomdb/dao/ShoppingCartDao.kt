package com.example.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.roomdb.entities.CartItem
import com.example.data.roomdb.relations.ShoppingCartWithCartItems
import com.example.data.roomdb.relations.UserAndShoppingCart

@Dao
abstract class ShoppingCartDao {
    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    abstract suspend fun getShoppingCart(userId: Int): UserAndShoppingCart

    @Transaction
    @Query("SELECT * FROM shoppingcart WHERE cartId = :cartId")
    abstract suspend fun getCartItems(cartId: Int): ShoppingCartWithCartItems

    @Transaction
    suspend fun getCartItemsForUser(userId: Int): List<CartItem> {
        val shoppingCart = getShoppingCart(userId)
        return getCartItems(shoppingCart.cart.cartId).cartItems
    }
}