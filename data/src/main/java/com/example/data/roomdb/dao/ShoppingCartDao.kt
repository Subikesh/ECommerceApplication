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
    abstract fun addCartItem(cart: CartItem): Long

    @Insert
    abstract fun addShoppingCart(cart: ShoppingCart): Long

    @Transaction
    @Insert
    fun addCartItemToUser(user: User, product: Product): Long {
        val cartId = getShoppingCart(user.userId)
        return addCartItem(CartItem(cartId.cart.cartId, product.productId))
    }

    /**
     * Create new shopping cart with only one product
     */
    @Transaction
    @Insert
    fun buyProductForUser(user:User, product: Product): ShoppingCart {
        val cart = ShoppingCart(userId = user.userId, total = product.discountPrice)
        val cartId = addShoppingCart(cart)
        cart.cartId = cartId.toInt()
        addCartItem(CartItem(cartId.toInt(), product.productId))
        deleteShoppingCart(cart)
        return cart
    }

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    abstract fun getShoppingCart(userId: Int): UserAndShoppingCart

    @Transaction
    @Query("SELECT * FROM shoppingcart WHERE cartId = :cartId")
    abstract fun getCartItems(cartId: Int): ShoppingCartWithCartItems

    @Transaction
    @Query("SELECT * FROM shoppingcart WHERE userId = :userId")
    abstract fun getCartItemsForUser(userId: Int): ShoppingCartWithCartItems

    @Transaction
    @Query("SELECT * FROM product WHERE productId = :productId")
    abstract fun getProductFromCartItem(productId: String): CartItemAndProduct

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM shoppingcart, cartitem WHERE shoppingcart.cartId = cartitem.cartId AND shoppingcart.userId = :userId AND cartitem.productId = :productId")
    abstract fun findCartItem(userId: Int, productId: String): ShoppingCartWithCartItems?

    @Query("UPDATE shoppingcart SET total = :price WHERE cartId = :cartId")
    abstract fun updateCartPrice(cartId: Int, price: Double)

    @Update
    abstract fun updateCartItem(cartItem: CartItem)

    @Delete
    abstract fun deleteCartItem(cart: CartItem)

    @Delete
    abstract fun deleteShoppingCart(cart: ShoppingCart)

}