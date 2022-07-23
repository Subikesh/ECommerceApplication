package com.example.data.repository

import com.example.data.mapper.ProductEntityMapperImpl
import com.example.data.mapper.UserEntityMapperImpl
import com.example.data.roomdb.dao.ProductDao
import com.example.data.roomdb.dao.ShoppingCartDao
import com.example.data.roomdb.entities.CartItem
import com.example.data.roomdb.entities.ShoppingCart
import com.example.data.roomdb.relations.CartItemAndProduct
import com.example.domain.models.Product
import com.example.domain.models.User
import javax.inject.Inject

class UserShoppingCart @Inject constructor(private val productDao: ProductDao, private val cartDao: ShoppingCartDao) {

    /** Add a new product to user's shopping cart */
    suspend fun addCartItem(_user: User, _product: Product) {
        val product = ProductEntityMapperImpl.toEntity(_product)
        val user = UserEntityMapperImpl.toEntity(_user)
        productDao.insert(product)
        cartDao.addCartItemToUser(user, product)
    }

    /** Get the list of products saved in user's shopping cart */
    suspend fun getCartItem(_user: User): List<CartItem> {
        val user = UserEntityMapperImpl.toEntity(_user)

        return cartDao.getCartItemsForUser(user.userId).cartItems
    }

    suspend fun updateCartTotal(cartId: Int, price: Double) {
        cartDao.updateCartPrice(cartId, price)
    }

    suspend fun getProductsFromCartList(cartList: List<CartItem>): MutableList<CartItemAndProduct> {
        val products = mutableListOf<CartItemAndProduct>()
        for (cartItem in cartList) {
            products.add(cartDao.getProductFromCartItem(cartItem.productId))
        }
        return products
    }

    /** Check if this product is already in user's shopping cart */
    suspend fun isInCartItem(user: User, product: Product): Boolean {
        val userCartRef = cartDao.findCartItem(user.userId, product.productId)
        return userCartRef != null
    }

    suspend fun increaseProductQuantity(cartItem: CartItem, quantity: Int) {
        cartItem.quantity = quantity
        cartDao.updateCartItem(cartItem)
    }

    suspend fun removeShoppingCart(cart: ShoppingCart) = cartDao.deleteShoppingCart(cart)

    suspend fun removeCartItem(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }
}