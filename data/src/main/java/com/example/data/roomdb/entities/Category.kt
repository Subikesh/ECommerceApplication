package com.example.data.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.extensions.fromSlug

val categoryList = arrayOf(
    Category("7jv", "food_nutrition",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/7jv.json?expiresAt=1631636817845&sig=14860c3a1e52510ac8eb1aee1adf53df"),
    Category("ckf-czl", "televisions",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/7jv.json?expiresAt=1631636817845&sig=14860c3a1e52510ac8eb1aee1adf53df"),
    Category("tyy-n0e", "landline_phones",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/7jv.json?expiresAt=1631636817845&sig=14860c3a1e52510ac8eb1aee1adf53df"),
    Category("6bo-ul6", "tv_video_accessories",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/7jv.json?expiresAt=1631636817845&sig=14860c3a1e52510ac8eb1aee1adf53df"),
    Category("6bo-5hp", "software",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/7jv.json?expiresAt=1631636817845&sig=14860c3a1e52510ac8eb1aee1adf53df")
)

val productMap = getProductMap(categoryList)

@Entity(tableName = "category")
data class Category(
    @PrimaryKey val categoryId: String,
    val title_slug: String,
    val productsUrl: String
) {
    val title: String
        get() = title_slug.fromSlug()

    // TODO: modify this based on new database model
    val productList: Array<Product>?
        get() = productMap[categoryId]

}