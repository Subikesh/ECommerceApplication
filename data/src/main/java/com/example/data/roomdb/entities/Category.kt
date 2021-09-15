package com.example.data.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.extensions.fromSlug
import com.example.data.models.getProductMapHome

val categoryList = listOf(
    Category("7jv", "food_nutrition",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/7jv.json?expiresAt=1631722799224&sig=cadd589dab13cb7de38747f9d9b08215"),
    Category("ckf-czl", "televisions",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/ckf-czl.json?expiresAt=1631717988870&sig=656827815aa0a625722c52618408465e"),
    Category("tyy-n0e", "landline_phones",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/tyy-n0e.json?expiresAt=1631722799223&sig=1f60bcc069a40b3d1cb51acc37c5077c"),
    Category("xfw", "e_learning",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/xfw.json?expiresAt=1631723300674&sig=e5cfb2c143805d1289ef1edb4a9cdce8"),
    Category("6bo-5hp", "software",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/6bo-5hp.json?expiresAt=1631722799224&sig=c47d958421fef8c4176c17691911fd0e")
)

@Entity(tableName = "category")
data class Category(
    @PrimaryKey val categoryId: String,
    val title_slug: String,
    val productsUrl: String
) {
    val title: String
        get() = title_slug.fromSlug()
}