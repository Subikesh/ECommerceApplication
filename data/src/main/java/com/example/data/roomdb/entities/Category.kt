package com.example.data.roomdb.entities

import com.example.data.extensions.fromSlug

val categoryList = arrayOf(
    Category("food_nutrition", "7jv"),
    Category("televisions", "ckf-czl"),
    Category("landline_phones", "tyy-n0e"),
    Category("tv_video_accessories", "6bo-ul6"),
    Category("software", "6bo-5hp")
)

val productMap = getProductMap(categoryList)

data class Category(
    val title_slug: String,
    val deltaUrl: String,
    val version: String = "",
    val productsUrl: String = "",
) {
    // TODO: Get the category id from the delta url api call
    val categoryId: String get() = deltaUrl

    val title: String
        get() = title_slug.fromSlug()

    // TODO: modify this based on new database model
    val productList: Array<Product>?
        get() = productMap[categoryId]

}