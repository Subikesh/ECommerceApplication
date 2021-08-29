package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.data.db.DatabaseContract
import com.example.data.db.DatabaseContract.User.COL_EMAIL
import com.example.data.db.DatabaseContract.User.COL_PASSWORD
import com.example.data.db.DatabaseContract.User.COL_USERNAME

/**
 * User entity/table in room database
 */
@Entity(
    tableName = DatabaseContract.User.TABLE_NAME,
    indices = [Index(value = [COL_EMAIL], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = COL_USERNAME) val username: String,
    @ColumnInfo(name = COL_PASSWORD) val password: String,
    @ColumnInfo(name = COL_EMAIL) val email: String
)