package com.example.ecommerceapplication.ui.user

import android.app.Application
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.data.entities.User
import com.example.data.usecases.Authentication
import com.example.ecommerceapplication.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val authentication = Authentication(application)
    private val TAG = "UserViewModel"

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

    suspend fun createUser(
        mailText: String,
        passText: String,
        confirmPassword: String,
        userText: String
    ): Boolean {
        return if (passText == confirmPassword) {
            user = authentication.userSignup(mailText, passText, userText)
            true
        } else false
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is user Fragment"
    }
    val text: LiveData<String> = _text
}