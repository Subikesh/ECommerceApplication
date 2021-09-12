package com.example.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.roomdb.dao.UserDao
import com.example.data.roomdb.entities.User

/**
 * Abstract class extending the RoomDatabase
 */
@Database(
    entities = [User::class],
    version = DatabaseContract.DATABASE_VERSION,
    exportSchema = false
)
abstract class EcommerceDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}