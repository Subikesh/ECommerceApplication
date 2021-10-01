package com.example.data.usecases

import android.content.Context
import com.example.data.repository.ProductEntityMapperImpl
import com.example.data.repository.UserEntityMapperImpl
import com.example.data.roomdb.DatabaseContract
import com.example.data.roomdb.entities.CartItem
import com.example.data.roomdb.entities.ShoppingCart
import com.example.data.roomdb.relations.CartItemAndProduct
import com.example.domain.models.Product
import com.example.domain.models.User

class UserShoppingCart(context: Context) {
    /** Database object to access database */
    private val db = DatabaseContract.getInstance(context)

    /** Add a new product to user's shopping cart */
    suspend fun addCartItem(_user: User, _product: Product) {
        val product = ProductEntityMapperImpl.toEntity(_product)
        val user = UserEntityMapperImpl.toEntity(_user)
        db.productDao().insert(product)
        db.cartDao().addCartItemToUser(user, product)
    }

    /** Get the list of products saved in user's shopping cart */
    suspend fun getCartItem(_user: User): List<CartItem> {
        val user = UserEntityMapperImpl.toEntity(_user)

        return db.cartDao().getCartItemsForUser(user.userId).cartItems
    }

    suspend fun updateCartTotal(cartId: Int, price: Double) {
        db.cartDao().updateCartPrice(cartId, price)
    }

    suspend fun getProductsFromCartList(cartList: List<CartItem>): MutableList<CartItemAndProduct> {
        val products = mutableListOf<CartItemAndProduct>()
        for (cartItem in cartList) {
            products.add(db.cartDao().getProductFromCartItem(cartItem.productId))
        }
        return products
    }

    /** Check if this product is already in user's shopping cart */
    suspend fun isInCartItem(user: User, product: Product): Boolean {
        val userCartRef = db.cartDao().findCartItem(user.userId, product.productId)
        return userCartRef != null
    }

    suspend fun increaseProductQuantity(cartItem: CartItem, quantity: Int) {
        cartItem.quantity = quantity
        db.cartDao().updateCartItem(cartItem)
    }

    suspend fun removeShoppingCart(cart: ShoppingCart) = db.cartDao().deleteShoppingCart(cart)

    suspend fun removeCartItem(cartItem: CartItem) {
        db.cartDao().deleteCartItem(cartItem)
    }
}