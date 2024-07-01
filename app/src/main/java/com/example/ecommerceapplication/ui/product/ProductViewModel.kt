package com.example.ecommerceapplication.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.session.SessionManager
import com.example.data.repository.UserOrders
import com.example.data.repository.UserShoppingCart
import com.example.data.repository.UserWishlist
import com.example.domain.models.Product
import com.example.ecommerceapplication.util.ToastDuration
import com.example.ecommerceapplication.util.ToastUtil
import kotlinx.coroutines.launch

class ProductViewModel constructor(
    private val session: SessionManager,
    private val userWishlist: UserWishlist,
    private val userShoppingCart: UserShoppingCart,
    private val userOrders: UserOrders,
    private val toastUtil: ToastUtil
) : ViewModel() {

    private lateinit var product: Product

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
            toastUtil.displayToast("Product removed from your wishlist", ToastDuration.SHORT)
            false
        } else if (!session.login) {
            toastUtil.displayToast("Please login to wishlist any product", ToastDuration.SHORT)
            false
        } else {
            if (session.login)
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

    class Factory(
        private val session: SessionManager,
        private val userWishlist: UserWishlist,
        private val userShoppingCart: UserShoppingCart,
        private val userOrders: UserOrders,
        private val toastUtil: ToastUtil
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProductViewModel(session, userWishlist, userShoppingCart, userOrders, toastUtil) as T
        }
    }
}