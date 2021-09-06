package com.example.ecommerceapplication.ui.user

import android.app.Application
import android.util.Log
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

    /**
     * Login user with mailId and password
     * @return true if user successfully logged in and false if not
     */
    fun loginUser(mailId: String, password: String): Boolean {
        var user: User?
        // User data will be null as coroutine runs synchronously
        // TODO: Change this to launch and return user inside launch
        runBlocking {
            user = authentication.userLogin(mailId, password)
            Log.d(TAG, "loginUser: Inside launch $user")
        }
        Log.d(TAG, "loginUser: Inside User login $user")
        return user != null

    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is user Fragment"
    }
    val text: LiveData<String> = _text
}