package com.example.data.usecases

import android.content.Context
import com.example.data.repository.ProductEntityMapperImpl
import com.example.data.repository.UserEntityMapperImpl
import com.example.data.roomdb.DatabaseContract
import com.example.data.roomdb.entities.CartItem
import com.example.data.roomdb.entities.Order
import com.example.data.roomdb.entities.ShoppingCart
import com.example.data.roomdb.relations.OrderWithCartItems
import com.example.domain.models.Product
import com.example.domain.models.User

class UserOrders(context: Context) {
    /** Database object to access database */
    private val db = DatabaseContract.getInstance(context)

    /** Add a new product to user's shopping cart */
    suspend fun moveCartToOrder(_user: User, _product: Product) {
        val product = ProductEntityMapperImpl.toEntity(_product)
        val user = UserEntityMapperImpl.toEntity(_user)
        db.productDao().insert(product)
        val cart = db.cartDao().getShoppingCart(user.userId)
        db.cartDao().deleteShoppingCart(cart.cart)
        db.orderDao().addOrder(Order(cart.cart.cartId, user.userId, cart.cart.total))
        // Creating new shopping cart for user
        db.cartDao().addShoppingCart(ShoppingCart(cart.cart.cartId, user.userId))
    }

    /** Get the list of products saved in user's shopping cart */
    suspend fun getCartItem(_user: User): List<OrderWithCartItems> {
        val user = UserEntityMapperImpl.toEntity(_user)
        return db.orderDao().getOrderCartsForUser(user.userId)
    }
}