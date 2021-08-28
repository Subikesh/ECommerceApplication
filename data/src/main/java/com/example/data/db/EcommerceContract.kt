package com.example.data.db

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import android.widget.Toast

object EcommerceContract {
    const val DATABASE_VERSION = 2

    /**
     * Local file name of the database
     */
    const val DATABASE_NAME = "ecommerce.db"

    /**
     * Data types for database columns
     */
    private const val TEXT_TYPE = "TEXT"
    private const val INT_TYPE = "INTEGER"

    /**
     * User table contract
     */
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "user"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_EMAIL = "email"

        const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$_ID $INT_TYPE PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_EMAIL $TEXT_TYPE UNIQUE NOT NULL, " +
                "$COLUMN_USERNAME $TEXT_TYPE NOT NULL, " +
                "$COLUMN_PASSWORD $TEXT_TYPE NOT NULL);"
        const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME;"

        /**
         * Inserts a new user to the table with the given parameters
         * @param context activity fragment
         * @param userMail User's email
         * @param password User password
         * @return user id inserted. -1 if some error occurs
         */
        fun addEntry(context: Context, userMail: String, password: String, email: String): Long {

            val db = EcommerceDbHelper(context).writableDatabase

            val values = ContentValues().apply {
                put(COLUMN_USERNAME, userMail)
                put(COLUMN_PASSWORD, password)
                put(COLUMN_EMAIL, email)
            }

            val inserted = db.insert(TABLE_NAME, null, values)
            db.close()
            return inserted
        }

        fun findEntry(context: Context, email: String, password: String): Boolean {
            val db = EcommerceDbHelper(context).writableDatabase

            val projection = arrayOf(_ID, COLUMN_USERNAME)
            val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
            val selectionArgs = arrayOf(email, password)

            val cursor =
                db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if (cursor.count <= 0) {
                Toast.makeText(context, "Username or password incorrect", Toast.LENGTH_SHORT).show()
                cursor.close()
                return false
            }
            Toast.makeText(context, "$email logged in!", Toast.LENGTH_SHORT).show()
            cursor.close()
            return true
        }
    }

}