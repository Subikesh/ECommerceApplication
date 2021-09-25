package com.example.data.roomdb.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["userId"], unique = true)])
data class ShoppingCart(
    @PrimaryKey(autoGenerate = true) val cartId: Int = 0,
    val userId: Int,
    @ColumnInfo(defaultValue = "0.0") val total: Double = 0.0
)