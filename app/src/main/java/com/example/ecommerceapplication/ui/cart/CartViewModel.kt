package com.example.ecommerceapplication.ui.cart

import android.app.Application
import androidx.lifecycle.*
import com.example.data.roomdb.entities.CartItem
import com.example.data.roomdb.relations.CartItemAndProduct
import com.example.data.session.SessionManager
import com.example.data.usecases.UserShoppingCart
import kotlinx.coroutines.launch

class CartViewModel(context: Application) : AndroidViewModel(context) {

    private val session = SessionManager(context)
    private val userShoppingCart = UserShoppingCart(context)

    suspend fun getCartList(): List<CartItem>? {
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