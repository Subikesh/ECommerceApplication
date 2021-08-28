package com.example.data.usecases

import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.data.db.DatabaseContract
import com.example.data.entities.User
import com.example.data.session.SessionManager

class Authentication(context: Context) {
    /** Database object to access database */
    private val db = DatabaseContract.getInstance(context)

    /** For keeping track of user login */
    private val session = SessionManager(context)

    /**
     * Login user using user mail and password
     * @param email     User's mail id
     * @param password  User password
     * @return the user entity who is logged in
     */
    suspend fun userLogin(email: String, password: String): User {
        val user = db.userDao().findLogin(email, password)
        userLogin(user)
        Log.i("LoginFragment", "userLogin: $user logged in")
        return user
    }

    /**
     * Utility function to login with user entity
     * @param user  User entity object to login
     */
    private fun userLogin(user: User) {
        session.login = true
        session.user = user
    }

    /**
     * Create new user if email is not already present
     * @param email     user's email
     * @param password  user's password
     * @param username  user's username
     * @return newly created user if successful, else null
     */
    suspend fun userSignup(email: String, password: String, username: String): User? {
        val insertUser = User(username = username, password = password, email = email)
        var returnCode: Long? = null
        try {
            returnCode = db.userDao().insert(insertUser)
            Log.d("LoginFragment", "userSignup: user insert return code: $returnCode")
            userLogin(insertUser)
            return insertUser
        } catch (e: SQLiteException) {
            return null
        }
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