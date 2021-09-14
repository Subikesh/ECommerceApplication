package com.example.data.api.models

data class ProductsList(
    val lastProductId: String,
    val nextUrl: String,
    val products: List<Product>,
    val validTill: Long
)