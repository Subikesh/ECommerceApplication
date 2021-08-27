package com.example.data.dao

import androidx.room.*
import com.example.data.db.DatabaseContract.User.COL_EMAIL
import com.example.data.db.DatabaseContract.User.COL_PASSWORD
import com.example.data.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE $COL_EMAIL = :email AND $COL_PASSWORD = :password LIMIT 1")
    suspend fun findLogin(email: String, password: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: User)

    @Delete
    suspend fun delete(user: User)
}