package com.example.data.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Price(val value: Double, val currency: String = "INR")

@Entity
data class Product(
    @PrimaryKey val productId: String,
    val title: String,
    val categoryId: String,
    val imageUrl: String,
    val maximumRetailPrice: Price,
    val discountPrice: Price,
    val description: String,
    val brand: String?,
    val codAvailable: Boolean = true,
    val productUrl: String? = null,
    // Set this as false if product not in stock
    val inStock: Boolean = true,
    // TODO: get the specification list(table) in some other class (general and dimensions)
    val specifications: Array<String>? = null,
    // Remarks is the important note for each product
    val remarks: String? = null
) {
    val discountPercent: Double
        get() =
            (maximumRetailPrice.value - discountPrice.value) * 100 / maximumRetailPrice.value

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
