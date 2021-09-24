package com.example.data.usecases

import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.data.repository.UserEntityMapperImpl
import com.example.data.roomdb.DatabaseContract
import com.example.data.roomdb.entities.User
import com.example.data.session.SessionManager

/**
 * Class contains helper methods for user authentication
 */
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
    suspend fun userLogin(email: String, password: String): com.example.domain.models.User? {
        val user = db.userDao().findLogin(email, password)
        return if (user != null) {
            userLogin(user)
            Log.i("LoginFragment", "userLogin: $user logged in")
            session.user!!
        } else
            null
    }

    /**
     * Utility function to login with user entity
     * @param user  User entity object to login
     */
    private fun userLogin(user: User) {
        session.login = true
        session.user = UserEntityMapperImpl.fromEntity(user)
    }

    /**
     * Create new user if email is not already present
     * @param email     user's email
     * @param password  user's password
     * @param username  user's username
     * @return newly created user if successful, else null
     */
    suspend fun userSignup(email: String, password: String, username: String): com.example.domain.models.User? {
        val insertUser = User(username = username, password = password, email = email)
        val returnCode: Long?
        return try {
            returnCode = db.userDao().insert(insertUser)
            Log.d("LoginFragment", "userSignup: userId: $returnCode created")
            userLogin(insertUser)
            UserEntityMapperImpl.fromEntity(insertUser)
        } catch (e: SQLiteException) {
            null
        }
    }

    /**
     * Logout current user if user logged in
     * @return logged out user entity. Null if user was not logged in
     */
    fun userLogout(): com.example.domain.models.User? {
        val user = session.user
        session.login = false
        session.user = null
        return user
    }
}