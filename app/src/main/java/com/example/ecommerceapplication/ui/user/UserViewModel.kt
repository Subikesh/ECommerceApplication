package com.example.ecommerceapplication.ui.user

import android.app.Application
import androidx.lifecycle.*
import com.example.domain.models.User
import com.example.data.usecases.Authentication

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val authentication = Authentication(application)

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

    private val _text = MutableLiveData<String>().apply {
        value = "This is user Fragment"
    }
    val text: LiveData<String> = _text
}