package com.example.data.db

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import android.widget.Toast

object EcommerceContract {
    const val DATABASE_VERSION = 1

    /**
     * Local file name of the database
     */
    const val DATABASE_NAME = "ecommerce.db"

    /**
     * Data types for database columns
     */
    private const val TEXT_TYPE = "TEXT"
    private const val INT_TYPE = "INT"

    /**
     * User table contract
     */
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "User"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_EMAIL = "email"

        const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$_ID $INT_TYPE PRIMARY KEY, " +
                "$COLUMN_EMAIL $TEXT_TYPE UNIQUE NOT NULL" +
                "$COLUMN_USERNAME $TEXT_TYPE NOT NULL, " +
                "$COLUMN_PASSWORD $TEXT_TYPE NOT NULL);"
        const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME;"

        fun addEntry(context: Context, username: String, password: String, email: String) {

            val db = EcommerceDbHelper(context).writableDatabase

            val values = ContentValues().apply {
                put(COLUMN_USERNAME, username)
                put(COLUMN_PASSWORD, password)
                put(COLUMN_EMAIL, email)
            }

            db.insert(TABLE_NAME, null, values)
            db.close()
        }

        fun findEntry(context: Context, username: String, password: String): Boolean {
            val db = EcommerceDbHelper(context).writableDatabase

            val projection = arrayOf(_ID, COLUMN_USERNAME)
            val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
            val selectionArgs = arrayOf(username, password)

            val cursor =
                db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if (cursor.count <= 0) {
                Toast.makeText(context, "Username or password incorrect", Toast.LENGTH_SHORT).show()
                cursor.close()
                return false
            }
            Toast.makeText(context, "$username logged in!", Toast.LENGTH_SHORT).show()
            cursor.close()
            return true
        }
    }

}