package com.example.data.roomdb.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.roomdb.entities.ShoppingCart
import com.example.data.roomdb.entities.User

/** One to one relation between User and ShoppingCart */
data class UserAndShoppingCart(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val cart: ShoppingCart
)