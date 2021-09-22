package com.example.data.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.Price

@Entity
data class Product(
    @PrimaryKey val productId: String,
    val title: String,
    val categoryId: String,
    val imageUrl: String,
    val bigImageUrl: String,
    val maximumRetailPrice: Price,
    val discountPrice: Price,
    val description: String,
    val brand: String?,
    val codAvailable: Boolean = true,
    val productUrl: String? = null,
    val inStock: Boolean = true,
    val discountPercent: Double,
    // TODO: get the specification list(table) in some other entity (general and dimensions)
    val specifications: Array<String>? = null,
    val remarks: String? = null
) {
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
