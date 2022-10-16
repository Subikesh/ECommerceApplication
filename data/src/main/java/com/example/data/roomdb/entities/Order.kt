package com.example.data.roomdb.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Order(
    @PrimaryKey val orderId: Int,
    val userId: Int,
    @ColumnInfo(defaultValue = "0.0") val total: Double = 0.0,
    @ColumnInfo(defaultValue = "false") val isSuccessful: Boolean = false,
    @ColumnInfo(name = "created_at") var createdAt: Long = 0
)
