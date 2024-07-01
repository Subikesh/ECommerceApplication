package com.example.ecommerceapplication.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.session.SessionManager
import com.example.data.repository.Authentication
import com.example.data.repository.UserWishlist
import com.example.domain.models.Product
import com.example.domain.models.User

class UserViewModel constructor(
    private val authentication: Authentication,
    private val session: SessionManager,
    private val userWishlist: UserWishlist
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
            products = userWishlist.getWishlist(session.user!!)
        return products
    }

    class Factory(
        private val authentication: Authentication,
        private val session: SessionManager,
        private val userWishlist: UserWishlist
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserViewModel(authentication, session, userWishlist) as T
        }
    }
}