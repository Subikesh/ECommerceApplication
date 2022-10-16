package com.example.ecommerceapplication.ui.user

import androidx.lifecycle.ViewModel
import com.example.data.repository.Authentication
import com.example.data.session.SessionManager
import com.example.domain.models.Product
import com.example.domain.models.User
import com.example.domain.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authentication: Authentication,
    private val session: SessionManager,
    private val WishlistRepository: WishlistRepository
) : ViewModel() {

    var user: User? = null
        private set

    /**
     * Login user with mailId and password
     * @param mailId    mail id of user
     * @param password  password of user
     */
    suspend fun loginUser(mailId: String, password: String) {
        user = authentication.userLogin(mailId, password)
    }

    /**
     * Create a new user with given user details
     * @param mailId    mail id of user
     * @param password  password of user
     * @param username  username
     * @return true if user registered; false if passwords don't match
     */
    suspend fun createUser(
        mailId: String,
        password: String,
        confirmPassword: String,
        username: String
    ): Boolean {
        return if (password == confirmPassword) {
            user = authentication.userSignup(mailId, password, username)
            true
        } else false
    }

    /**
     * Logout currently logged in user
     */
    fun logoutUser() {
        authentication.userLogout()
    }

    /**
     * Get the product list saved in wishlist
     */
    suspend fun getWishlist(): List<Product>? {
        var products: List<Product>? = null
        if (session.user != null)
            products = WishlistRepository.getWishlist(session.user!!)
        return products
    }
}