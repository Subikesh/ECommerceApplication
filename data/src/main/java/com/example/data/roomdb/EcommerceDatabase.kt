package com.example.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.roomdb.dao.CategoryDao
import com.example.data.roomdb.dao.ProductDao
import com.example.data.roomdb.dao.UserDao
import com.example.data.roomdb.dao.WishlistDao
import com.example.data.roomdb.entities.*

/**
 * Abstract class extending the RoomDatabase
 */
@Database(
    entities = [User::class, Category::class, Product::class, UserProductCrossRef::class],
    version = DatabaseContract.DATABASE_VERSION,
    exportSchema = false
)
abstract class EcommerceDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun wishlistDao(): WishlistDao
}