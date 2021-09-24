package com.example.data.roomdb.entities

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(primaryKeys = ["cartId", "productId"])
data class CartItem(
    val cartId: Int,
    val productId: String,
    @ColumnInfo(defaultValue = "1") var quantity: Int = 1
)