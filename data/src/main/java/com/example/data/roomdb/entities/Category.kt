package com.example.data.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey val categoryId: String,
    val title_slug: String,
    val title: String,
    val productsUrl: String
)