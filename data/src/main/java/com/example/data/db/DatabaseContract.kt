package com.example.data.db

import android.content.Context
import androidx.room.Room

object DatabaseContract {
    const val DATABASE_VERSION = 2

    /**
     * Local file name of the database
     */
    const val DATABASE_NAME = "ecommerce"

    var databaseInstance: EcommerceDatabase? = null

    /**
     * Singleton implementation of retrieving database object
     * @param context context to create room database
     * @return created database instance or retrieving existing db instance
     */
    fun getInstance(context: Context): EcommerceDatabase {
        if (databaseInstance == null) {
            synchronized(EcommerceDatabase::class.java) {
                databaseInstance = buildRoomDb(context)
            }
        }
        return databaseInstance!!
    }

    private fun buildRoomDb(context: Context) = Room.databaseBuilder(
            context,
            EcommerceDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    object User {
        const val TABLE_NAME = "User"
        const val COL_USERNAME = "username"
        const val COL_PASSWORD = "password"
        const val COL_EMAIL = "email"
    }
}