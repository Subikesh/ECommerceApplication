package com.example.data.roomdb.entities

data class OrderCartItem(
    val orderId: Int,
    val userId: Int,
    val total: Double,
    val isSuccessful: Boolean,
    val createdAt: Long,
    val quantity: Int,
    val productId: String,
    var product: Product? = null
)