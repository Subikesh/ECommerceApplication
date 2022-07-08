package com.example.ecommerceapplication.ui.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.data.session.SessionManager
import com.example.data.usecases.Authentication
import com.example.data.usecases.UserWishlist
import com.example.domain.models.Product
import com.example.domain.models.User
import com.example.ecommerceapplication.di.ViewModelAssistedFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class UserViewModel @AssistedInject constructor(
    @Assisted private val stateHandle: SavedStateHandle,
    private val authentication: Authentication,
    private val session: SessionManager,
    private val userWishlist: UserWishlist
) : ViewModel() {

    @dagger.assisted.AssistedFactory
    interface Factory : ViewModelAssistedFactory<UserViewModel>

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
}