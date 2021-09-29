package com.example.ecommerceapplication.ui.product

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.session.SessionManager
import com.example.data.usecases.UserOrders
import com.example.data.usecases.UserShoppingCart
import com.example.data.usecases.UserWishlist
import com.example.domain.models.Product
import kotlinx.coroutines.launch

class ProductViewModel(val context: Application) : AndroidViewModel(context) {

    private lateinit var product: Product
    private val session = SessionManager(context)

    private val userWishlist = UserWishlist(context)
    private val userShoppingCart = UserShoppingCart(context)
    private val userOrders = UserOrders(context)

    internal fun setProduct(_product: Product) {
        product = _product
    }

    suspend fun inWishlist() =
        session.login && userWishlist.isInWishlist(session.user!!, product)

    /**
     * Toggles wishlist status of a product
     * @return  false if it is already in wishlist or if user is logged out
     *          true if it is not in wishlist and user is logged in
     */
    suspend fun wishlistProduct(): Boolean {
        val inWishlist = inWishlist()
        return if (inWishlist) {
            userWishlist.removeWishlist(session.user!!, product)
            Toast.makeText(context, "Product removed from your wishlist", Toast.LENGTH_SHORT).show()
            false
        } else {
            if (!session.login)
                Toast.makeText(context, "Please login to wishlist any product", Toast.LENGTH_SHORT).show()
            else
                userWishlist.addWishlist(session.user!!, product)
            session.login
        }
    }

    suspend fun isInCart(): Boolean {
        return if (session.login) {
            userShoppingCart.isInCartItem(session.user!!, product)
        } else
            false
    }

    fun addProductToCart(): Boolean {
        return if (session.login) {
            viewModelScope.launch {
                userShoppingCart.addCartItem(session.user!!, product)
            }
            true
        } else false
    }

    suspend fun buyProduct() = userOrders.buyProduct(session.user!!, product)

    fun isLoggedIn() = session.login
}