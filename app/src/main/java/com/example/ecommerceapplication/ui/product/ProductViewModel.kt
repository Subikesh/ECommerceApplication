package com.example.ecommerceapplication.ui.product

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.data.session.SessionManager
import com.example.data.usecases.UserWishlist
import com.example.domain.models.Product

class ProductViewModel(val context: Application) : AndroidViewModel(context) {

    private lateinit var product: Product
    private val session = SessionManager(context)
    private val userWishlist = UserWishlist(context)

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
}