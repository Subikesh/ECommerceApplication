package com.example.data.db

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.dao.UserDao
import com.example.data.entities.User

@Database(entities = [User::class], version = DatabaseContract.DATABASE_VERSION)
abstract class EcommerceDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}