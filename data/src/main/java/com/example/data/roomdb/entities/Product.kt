package com.example.data.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

val modelProduct = Product(
    "TVSEJGYMKVXHBQH8",
    "Lloyd 61 cm (24 inch) HD Ready LED TV(L24BC)",
    "7jv",
    "https://rukminim1.flixcart.com/image/200/200/television/q/h/8/lloyd-l24bc-original-imaejhyhfgtbchnk.jpeg?q=90",
    Price(14990.0), Price(11800.0),
    "", null
)

/**
 * Gets list of categories and returns a map for those categories to array of 10 model products
 * @param categoryList Array of ProductsList objects to create the map
 * @return Mutable map of ProductsList Id to Array of model products
 */
fun getProductMap(categoryList: Array<Category>): MutableMap<String, Array<Product>> {
    val productMap: MutableMap<String, Array<Product>> = mutableMapOf()
    for (category in categoryList) {
        productMap[category.categoryId] = Array(10) { modelProduct }
    }
    return productMap
}

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
