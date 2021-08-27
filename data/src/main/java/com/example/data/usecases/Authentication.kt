package com.example.data.usecases

import android.content.Context
import androidx.room.Room
import com.example.data.db.DatabaseContract
import com.example.data.db.EcommerceContract
import com.example.data.db.EcommerceDatabase
import com.example.data.entities.User
import com.example.data.session.SessionManager

class Authentication(context: Context) {
    /** Database object to access database */
    private val db = DatabaseContract.getInstance(context)

    /** For keeping track of user login */
    private val session = SessionManager(context)

    /**
     * Login user using user mail and password
     * @param email User's mail id
     * @param password User password
     * @return the user entity who is logged in
     */
    suspend fun userLogin(email: String, password: String): User {
        val user = db.userDao().findLogin(email, password)
        if (user != null) {
            session.login = true
            session.user = user
        }
        return user
    }

    /**
     * Logout current user if user logged in
     * @return logged out user entity. Null if user was not logged in
     */
    fun userLogout(): User? {
        val user = session.user
        session.login = false
        session.user = null
        return user
    }
}