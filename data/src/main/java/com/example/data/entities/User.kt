package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.db.DatabaseContract

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = DatabaseContract.User.COL_USERNAME) val username: String,
    @ColumnInfo(name = DatabaseContract.User.COL_PASSWORD) val password: String,
    @ColumnInfo(name = DatabaseContract.User.COL_EMAIL) val email: String
)