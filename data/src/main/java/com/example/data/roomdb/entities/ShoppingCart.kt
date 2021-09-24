package com.example.data.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingCart(
    @PrimaryKey(autoGenerate = true) val cartId: Int = 0,
    val userId: Int
    )