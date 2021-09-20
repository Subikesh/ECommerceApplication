package com.example.domain.models

data class Product(
    val productId: String,
    val title: String,
    val imageUrl: String,
    val maximumRetailPrice: Price,
    val discountPrice: Price,
    val description: String,
    val brand: String?,
    // Set this as false if product not in stock
    val codAvailable: Boolean = true,
    val productUrl: String? = null,
    val inStock: Boolean = true,
    // TODO: get the specification list(table) in some other class (general and dimensions)
    val specifications: Array<String>? = null,
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