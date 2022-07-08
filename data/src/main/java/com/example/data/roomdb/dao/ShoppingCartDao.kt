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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addCartItem(cart: CartItem): Long

    @Insert
    abstract suspend fun addShoppingCart(cart: ShoppingCart): Long

    @Transaction
    @Insert
    suspend fun addCartItemToUser(user: User, product: Product): Long {
        val cartId = getShoppingCart(user.userId)
        return addCartItem(CartItem(cartId.cart.cartId, product.productId))
    }

    /**
     * Create new shopping cart with only one product
     */
    @Transaction
    @Insert
    suspend fun buyProductForUser(user:User, product: Product): ShoppingCart {
        val cart = ShoppingCart(userId = user.userId, total = product.discountPrice)
        val cartId = addShoppingCart(cart)
        cart.cartId = cartId.toInt()
        addCartItem(CartItem(cartId.toInt(), product.productId))
        deleteShoppingCart(cart)
        return cart
    }

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    abstract suspend fun getShoppingCart(userId: Int): UserAndShoppingCart

    @Transaction
    @Query("SELECT * FROM shoppingcart WHERE cartId = :cartId")
    abstract suspend fun getCartItems(cartId: Int): ShoppingCartWithCartItems

    @Transaction
    @Query("SELECT * FROM shoppingcart WHERE userId = :userId")
    abstract suspend fun getCartItemsForUser(userId: Int): ShoppingCartWithCartItems

    @Transaction
    @Query("SELECT * FROM product WHERE productId = :productId")
    abstract suspend fun getProductFromCartItem(productId: String): CartItemAndProduct

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM shoppingcart, cartitem WHERE shoppingcart.cartId = cartitem.cartId AND shoppingcart.userId = :userId AND cartitem.productId = :productId")
    abstract suspend fun findCartItem(userId: Int, productId: String): ShoppingCartWithCartItems?

    @Query("UPDATE shoppingcart SET total = :price WHERE cartId = :cartId")
    abstract suspend fun updateCartPrice(cartId: Int, price: Double)

    @Update
    abstract suspend fun updateCartItem(cartItem: CartItem)

    @Delete
    abstract suspend fun deleteCartItem(cart: CartItem)

    @Delete
    abstract suspend fun deleteShoppingCart(cart: ShoppingCart)

}