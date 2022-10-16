package com.example.data.roomdb.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.data.roomdb.entities.Product
import com.example.data.roomdb.entities.User
import com.example.data.roomdb.entities.UserProductCrossRef

/** Wishlist: Many to many relation between user and product */
data class UserWithProducts(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "productId",
        associateBy = Junction(UserProductCrossRef::class)
    )
    val products: List<Product>
)