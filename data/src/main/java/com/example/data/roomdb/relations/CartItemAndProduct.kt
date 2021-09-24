package com.example.data.roomdb.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.roomdb.entities.CartItem
import com.example.data.roomdb.entities.Product

/** One to one relation between a shopping cart item and a product */
data class CartItemAndProduct(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val cartItem: CartItem
)
