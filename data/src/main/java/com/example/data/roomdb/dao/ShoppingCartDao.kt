package com.example.data.roomdb.dao

import androidx.room.*
import com.example.data.roomdb.entities.CartItem
import com.example.data.roomdb.entities.Product
import com.example.data.roomdb.entities.ShoppingCart
import com.example.data.roomdb.entities.User
import com.example.data.roomdb.relations.CartItemAndProduct
import com.example.data.roomdb.relations.ShoppingCartWithCartItems
import com.example.data.roomdb.relations.UserAndShoppingCart

@Dao
abstract class ShoppingCartDao {
    @Insert
    abstract suspend fun addCartItem(cart: CartItem): Long

    @Insert
    abstract suspend fun addShoppingCart(cart: ShoppingCart): Long

    @Insert
    suspend fun addCartItemToUser(user: User, product: Product): Long {
        val cartId = addShoppingCart(ShoppingCart(userId = user.userId))
        return addCartItem(CartItem(cartId.toInt(), product.productId))
    }

    @Delete
    abstract suspend fun removeCartItem(cart: CartItem)

    @Delete
    suspend fun removeCartItemForUser(user: User, product: Product) {
        removeCartItem(CartItem(cartId = getShoppingCart(user.userId).cart.cartId, product.productId))
    }

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    abstract suspend fun getShoppingCart(userId: Int): UserAndShoppingCart

    @Transaction
    @Query("SELECT * FROM shoppingcart WHERE cartId = :cartId")
    abstract suspend fun getCartItems(cartId: Int): ShoppingCartWithCartItems

    //TODO: If this doesn't work delete this
    @Transaction
    @Query("SELECT * FROM shoppingcart WHERE userId = :userId")
    abstract suspend fun getCartItemsForUser(userId: Int): ShoppingCartWithCartItems

//    @Transaction
//    suspend fun getCartItemsForUser(userId: Int): List<CartItem> {
//        val shoppingCart = getShoppingCart(userId)
//        return getCartItems(shoppingCart.cart.cartId).cartItems
//    }

    @Transaction
    @Query("SELECT * FROM product WHERE productId = :productId")
    abstract suspend fun getProductFromCartItem(productId: String): CartItemAndProduct

    @Transaction
    @Query("SELECT * FROM shoppingcart, cartitem WHERE shoppingcart.cartId = cartitem.cartId AND shoppingcart.userId = :userId AND cartitem.productId = :productId")
    abstract suspend fun findCartItem(userId: Int, productId: String): ShoppingCartWithCartItems

    @Update
    abstract suspend fun increaseProductQuantity(cartItem: CartItem)
}