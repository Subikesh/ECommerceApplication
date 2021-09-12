package com.example.data.roomdb

import android.content.Context
import androidx.room.Room

/**
 * Database information grouped as object
 */
object DatabaseContract {
    const val DATABASE_VERSION = 3

    /**
     * Local file name of the database
     */
    const val DATABASE_NAME = "ecommerce"

    var databaseInstance: EcommerceDatabase? = null

    /**
     * Singleton implementation of retrieving database object
     * @param context Context to create room database
     * @return Created database instance or retrieving existing db instance
     */
    fun getInstance(context: Context): EcommerceDatabase {
        if (databaseInstance == null) {
            synchronized(EcommerceDatabase::class.java) {
                databaseInstance = buildRoomDb(context)
            }
        }
        return databaseInstance!!
    }

    /**
     * Creates room database instance
     * @param context context from which database is called
     * @return Created database instance
     */
    private fun buildRoomDb(context: Context) = Room.databaseBuilder(
            context,
            EcommerceDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    /**
     * User table details
     */
    object User {
        const val TABLE_NAME = "User"
        const val COL_USERNAME = "username"
        const val COL_PASSWORD = "password"
        const val COL_EMAIL = "email"
    }
}