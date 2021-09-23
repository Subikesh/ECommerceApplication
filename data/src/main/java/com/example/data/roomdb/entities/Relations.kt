package com.example.data.roomdb.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/** Relating one to many for category to products */
data class CategoryWithProducts(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val products: List<Product>
)

/** Many to many relation between user and product used for wishlist */
data class UserWithProducts(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "productId",
        associateBy = Junction(UserProductCrossRef::class)
    )
    val products: List<Product>
)