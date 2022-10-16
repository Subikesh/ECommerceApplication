package com.example.data.roomdb.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.roomdb.entities.CartItem
import com.example.data.roomdb.entities.Order

data class OrderWithCartItems(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "cartId"
    )
    val orderItem: List<CartItem>
)
