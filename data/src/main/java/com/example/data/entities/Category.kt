package com.example.data.entities

val categoryList = arrayOf(
    Category("food_nutrition", "7jv"),
    Category("televisions", "ckf-czl"),
    Category("landline_phones", "tyy-n0e"),
    Category("tv_video_accessories", "6bo-ul6"),
    Category("software", "6bo-5hp")
)

val productMap = getProductMap(categoryList)

data class Category(
    val title: String,
    val deltaUrl: String,
    val version: String = "",
    val productsUrl: String = "",
) {
    // TODO: Get the category id from the delta url api call
    val categoryId: String get() = deltaUrl

    // TODO: modify this based on new database model
    val productList: Array<Product>?
        get() = productMap[categoryId]

}