package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.data.db.DatabaseContract.User.COL_EMAIL
import com.example.data.db.DatabaseContract.User.COL_PASSWORD
import com.example.data.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE $COL_EMAIL = :email AND $COL_PASSWORD = :password LIMIT 1")
    fun findLogin(email: String, password: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}