package com.example.data.roomdb.entities

import androidx.room.Entity


@Entity(primaryKeys = ["cartId", "productId"])
data class CartItem(
    val cartId: Int,
    val productId: String,
    val quantity: Int = 1
)