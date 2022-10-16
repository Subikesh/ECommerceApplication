package com.example.data.roomdb.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.roomdb.entities.Order
import com.example.data.roomdb.entities.User

data class UserWithOrders(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val orders: List<Order>
)