package com.example.data.roomdb.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

/** Many to many relation table for user and product */
@Entity(primaryKeys = ["userId", "productId"])
data class UserProductCrossRef(
    val userId: Int,
    @ColumnInfo(index = true) val productId: String
)