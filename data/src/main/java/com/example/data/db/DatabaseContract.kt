package com.example.data.db

import android.provider.BaseColumns

object DatabaseContract {
    const val DATABASE_VERSION = 1

    /**
     * Local file name of the database
     */
    const val DATABASE_NAME = "ecommerce.db"

    object User {
        const val TABLE_NAME = "User"
        const val COL_USERNAME = "username"
        const val COL_PASSWORD = "password"
        const val COL_EMAIL = "email"
    }
}