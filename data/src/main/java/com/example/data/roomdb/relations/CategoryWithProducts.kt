package com.example.data.roomdb.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.roomdb.entities.Category
import com.example.data.roomdb.entities.Product

/** Relating one to many from category to products */
data class CategoryWithProducts(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val products: List<Product>
)