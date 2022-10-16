package com.example.data.roomdb.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.roomdb.entities.CartItem
import com.example.data.roomdb.entities.ShoppingCart

/** One to many relation for list of cartItems in each shopping cart */
data class ShoppingCartWithCartItems(
    @Embedded val cart: ShoppingCart,
    @Relation(
        parentColumn = "cartId",
        entityColumn = "cartId"
    )
    val cartItems: List<CartItem>
)