package com.example.ecommerceapplication.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.mapper.OrderEntityMapper
import com.example.data.roomdb.entities.CartItem
import com.example.data.roomdb.entities.OrderCartItem
import com.example.data.roomdb.entities.ShoppingCart
import com.example.data.roomdb.relations.CartItemAndProduct
import com.example.data.session.SessionManager
import com.example.data.repository.UserOrders
import com.example.data.repository.UserShoppingCart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val session: SessionManager, private val userShoppingCart: UserShoppingCart, private val userOrder: UserOrders) : ViewModel() {

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
}