package com.example.data.repository

import com.example.data.mapper.ProductEntityMapperImpl
import com.example.data.mapper.UserEntityMapperImpl
import com.example.data.roomdb.dao.OrderDao
import com.example.data.roomdb.dao.ProductDao
import com.example.data.roomdb.dao.ShoppingCartDao
import com.example.data.roomdb.entities.Order
import com.example.data.roomdb.entities.ShoppingCart
import com.example.data.roomdb.relations.OrderWithCartItems
import com.example.domain.models.Product
import com.example.domain.models.User
import javax.inject.Inject

class UserOrders @Inject constructor(
    private val cartDao: ShoppingCartDao,
    private val productDao: ProductDao,
    private val orderDao: OrderDao
) {

    /** Remove user's shopping cart and transferred it to order history */
    suspend fun moveCartToOrder(_user: User, successful: Boolean = true) {
        val user = UserEntityMapperImpl.toEntity(_user)
        val userCart = cartDao.getShoppingCart(user.userId)
        cartDao.deleteShoppingCart(userCart.cart)
        buyCart(userCart.cart, successful)
        // Creating new shopping cart for user
        cartDao.addShoppingCart(ShoppingCart(userId = user.userId))
    }

    /** Add a single product to orders */
    suspend fun buyProduct(_user: User, _product: Product): ShoppingCart {
        val user = UserEntityMapperImpl.toEntity(_user)
        val product = ProductEntityMapperImpl.toEntity(_product)
        productDao.insert(product)
        return cartDao.buyProductForUser(user, product)
    }

    /** Get the list of products saved in user's shopping cart */
    suspend fun getCartItem(user: User): List<OrderWithCartItems> {
        return orderDao.getOrderCartsForUser(user.userId)
    }

    suspend fun buyCart(cart: ShoppingCart, isSuccessful: Boolean = true) =
        orderDao.addOrder(Order(cart.cartId, cart.userId, cart.total, isSuccessful))

    suspend fun getProduct(productId: String) = productDao.getProduct(productId)
}