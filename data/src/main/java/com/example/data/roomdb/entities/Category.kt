package com.example.data.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//val categoryList = listOf(
//    Category("7jv", "food_nutrition",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/7jv.json?expiresAt=1631896642689&sig=cab91fe6cba7339e8684f58beee5cefe"),
//    Category("ckf-czl", "televisions",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/ckf-czl.json?expiresAt=1631751320045&sig=55e4c323fd73e3556b9cd6c4233a9360"),
//    Category("2oq-s9b", "mens_clothing",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/2oq-s9b.json?expiresAt=1631751320044&sig=ac325f75f2ea54469562856c6129ab42"),
//    Category("xfw", "e_learning",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/xfw.json?expiresAt=1631751320045&sig=80c8204e2ec98c0f31853b819abb6368"),
//    Category("6bo-5hp", "software",  "https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/6bo-5hp.json?expiresAt=1631751320044&sig=56a31f3de60ff940277d9744acfd03aa")
//)

@Entity(tableName = "category")
data class Category(
    @PrimaryKey val categoryId: String,
    val title_slug: String,
    val title: String,
    val productsUrl: String
)