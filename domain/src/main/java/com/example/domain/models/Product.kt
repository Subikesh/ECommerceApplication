package com.example.domain.models

import java.io.Serializable

data class Product(
    val productId: String,
    val title: String,
    val categoryId: String,
    val imageUrl: String?,
    val bigImageUrl: String?,
    val maximumRetailPrice: Price,
    val discountPrice: Price,
    val description: String,
    val brand: String?,
    val codAvailable: Boolean = true,
    val productUrl: String? = null,
    // Set this as false if product not in stock
    val inStock: Boolean = true,
    val specifications: Array<String>? = null,
    val remarks: String? = null
) : Serializable {
    val discountPercent: Double
        get() =
            (maximumRetailPrice.amount - discountPrice.amount) * 100 / maximumRetailPrice.amount

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (productId != other.productId) return false

        return true
    }

    override fun hashCode(): Int {
        return productId.hashCode()
    }
}