package com.example.data.models

import com.example.data.roomdb.entities.Price

val modelProduct = Product(
    "TVSEJGYMKVXHBQH8",
    "Lloyd 61 cm (24 inch) HD Ready LED TV(L24BC)",
    "https://rukminim1.flixcart.com/image/200/200/television/q/h/8/lloyd-l24bc-original-imaejhyhfgtbchnk.jpeg?q=90",
    Price(14990.0), Price(11800.0),
    "", null
)

/**
 * Gets list of categories and returns a map for those categories to array of 10 model products
 * @param categoryList Array of ProductsList objects to create the map
 * @return Mutable map of ProductsList Id to Array of model products
 */
fun getProductMap(categoryList: List<Category>): MutableMap<String, List<Product>> {
    val productMap: MutableMap<String, List<Product>> = mutableMapOf()
    for (category in categoryList) {
        productMap[category.categoryId] = List(10) { modelProduct }
    }
    return productMap
}

// Dummy function to debug another use case of this
// TODO: Delete this function after implementing home page api calls
fun getProductMapHome(categoryList: List<com.example.data.roomdb.entities.Category>): MutableMap<String, List<Product>> {
    val productMap: MutableMap<String, List<Product>> = mutableMapOf()
    for (category in categoryList) {
        productMap[category.categoryId] = List(10) { modelProduct }
    }
    return productMap
}

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