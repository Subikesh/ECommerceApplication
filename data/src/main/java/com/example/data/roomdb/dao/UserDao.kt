package com.example.data.roomdb.dao

import androidx.room.*
import com.example.data.roomdb.entities.User

/** User data access object in room database */
@Dao
interface UserDao: BaseDao<User> {
    /**
     * Select all users from User table
     * @return List of users selected
     */
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    /**
     * Select one user with the given email and password
     * @param email     user email
     * @param password  user password
     * @return user selected from the query
     */
    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    fun findLogin(email: String, password: String): User?
}