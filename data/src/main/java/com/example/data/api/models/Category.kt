package com.example.data.api.models

data class Category(
    val lastProductId: String,
    val nextUrl: String,
    val products: List<Product>,
    val validTill: Long
)