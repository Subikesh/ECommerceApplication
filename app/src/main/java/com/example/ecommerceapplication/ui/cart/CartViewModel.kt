package com.example.ecommerceapplication.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.repository.OrderEntityMapper
import com.example.data.roomdb.entities.CartItem
import com.example.data.roomdb.entities.OrderCartItem
import com.example.data.roomdb.entities.ShoppingCart
import com.example.data.roomdb.relations.CartItemAndProduct
import com.example.data.session.SessionManager
import com.example.data.usecases.UserOrders
import com.example.data.usecases.UserShoppingCart
import kotlinx.coroutines.launch

class CartViewModel constructor(context: Application, private val session: SessionManager) : AndroidViewModel(context) {

    private val userShoppingCart = UserShoppingCart(context)
    private val userOrder = UserOrders(context)

    private suspend fun getCartList(): List<CartItem>? {
        return if (session.login) {
            val user = session.user!!
            userShoppingCart.getCartItem(user)
        } else
            null
    }

    suspend fun getCartAndProductList(): List<CartItemAndProduct>? {
        val cartList = getCartList()
        return if (cartList != null)
            userShoppingCart.getProductsFromCartList(cartList)
        else null
    }

    suspend fun getOrderList() : List<OrderCartItem>? {
        return if (session.login) {
            val orderEntities = userOrder.getCartItem(session.user!!)
            val orders = OrderEntityMapper.fromEntity(orderEntities)
            for (order in orders) {
                order.product = userOrder.getProduct(order.productId)
            }
            orders
        } else null
    }

    suspend fun makeOrder(cart: ShoppingCart, isSuccessful: Boolean = true) {
        userOrder.buyCart(cart, isSuccessful)
        userShoppingCart.removeShoppingCart(cart)
    }

    suspend fun moveCartToOrder(successful: Boolean = true) {
        userOrder.moveCartToOrder(session.user!!, successful)
    }

    fun increaseProductQuantity(cartItem: CartItem, quantity: Int) {
        viewModelScope.launch { userShoppingCart.increaseProductQuantity(cartItem, quantity) }
    }

    fun updateTotal(cartId: Int, price: Double) {
        viewModelScope.launch { userShoppingCart.updateCartTotal(cartId, price) }
    }

    fun deleteCartItem(cartItem: CartItem) {
        viewModelScope.launch { userShoppingCart.removeCartItem(cartItem) }
    }

    class Factory constructor(
        private val application: Application,
        private val session: SessionManager,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CartViewModel(application, session) as T
        }
    }
}