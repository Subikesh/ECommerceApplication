package com.example.data.api.models

import androidx.room.Ignore

data class ProductsList(
    val lastProductId: String,
    val nextUrl: String,
    val products: List<ProductResult>,
    val validTill: Long
) {
    @Ignore lateinit var categoryId: String

    fun setCategory(id: String) { categoryId = id }
}