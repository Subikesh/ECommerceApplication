package com.example.data.usecases

import android.content.Context
import com.example.data.repository.ProductEntityMapperImpl
import com.example.data.repository.UserEntityMapperImpl
import com.example.data.roomdb.DatabaseContract
import com.example.data.roomdb.entities.Order
import com.example.data.roomdb.entities.ShoppingCart
import com.example.data.roomdb.relations.OrderWithCartItems
import com.example.domain.models.Product
import com.example.domain.models.User

class UserOrders(context: Context) {
    /** Database object to access database */
    private val db = DatabaseContract.getInstance(context)

    /** Remove user's shopping cart and transferred it to order history */
    suspend fun moveCartToOrder(_user: User, successful: Boolean = true) {
        val user = UserEntityMapperImpl.toEntity(_user)
        val userCart = db.cartDao().getShoppingCart(user.userId)
        db.cartDao().deleteShoppingCart(userCart.cart)
        buyCart(userCart.cart, successful)
        // Creating new shopping cart for user
        db.cartDao().addShoppingCart(ShoppingCart(userId = user.userId))
    }

    /** Add a single product to orders */
    suspend fun buyProduct(_user: User, _product: Product): ShoppingCart {
        val user = UserEntityMapperImpl.toEntity(_user)
        val product = ProductEntityMapperImpl.toEntity(_product)
        db.productDao().insert(product)
        return db.cartDao().buyProductForUser(user, product)
    }

    /** Get the list of products saved in user's shopping cart */
    suspend fun getCartItem(user: User): List<OrderWithCartItems> {
        return db.orderDao().getOrderCartsForUser(user.userId)
    }

    suspend fun buyCart(cart: ShoppingCart, isSuccessful: Boolean = true) =
        db.orderDao().addOrder(Order(cart.cartId, cart.userId, cart.total, isSuccessful))

    suspend fun getProduct(productId: String) = db.productDao().getProduct(productId)
}