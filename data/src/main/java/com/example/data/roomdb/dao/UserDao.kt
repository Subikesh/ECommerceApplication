package com.example.data.roomdb.dao

import androidx.room.*
import com.example.data.roomdb.DatabaseContract.User.COL_EMAIL
import com.example.data.roomdb.DatabaseContract.User.COL_PASSWORD
import com.example.data.roomdb.entities.User

/**
 * User data access object in room database
 */
@Dao
interface UserDao {
    /**
     * Select all users from User table
     * @return List of users selected
     */
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    /**
     * Select one user with the given email and password
     * @param email     user email
     * @param password  user password
     * @return user selected from the query
     */
    @Query("SELECT * FROM user WHERE $COL_EMAIL = :email AND $COL_PASSWORD = :password LIMIT 1")
    suspend fun findLogin(email: String, password: String): User

    /**
     * Insert list of user entities
     * @param users list of user entity objects
     * @return success or error code if some conflict occurs
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(vararg users: User): List<Long>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    /**
     * Delete an user entry from the table
     * @param user user entity to be deleted
     */
    @Delete
    suspend fun delete(user: User)
}