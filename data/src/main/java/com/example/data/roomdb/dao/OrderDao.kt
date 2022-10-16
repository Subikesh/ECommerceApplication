package com.example.data.roomdb.dao

import androidx.room.*
import com.example.data.roomdb.entities.Order
import com.example.data.roomdb.relations.OrderWithCartItems
import com.example.data.roomdb.relations.UserWithOrders

@Dao
abstract class OrderDao : BaseDao<Order> {

    suspend fun addOrder(order: Order): Long {
        order.createdAt = System.currentTimeMillis()
        return insert(order)
    }

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    abstract suspend fun getOrders(userId: Int): UserWithOrders

    @Transaction
    @Query("SELECT * FROM `order` WHERE orderId = :orderId")
    abstract suspend fun getCartItems(orderId: Int): OrderWithCartItems

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT DISTINCT * FROM `order` AS o, cartitem AS c WHERE o.orderId = c.cartId AND o.userId = :userId GROUP BY o.orderId ORDER BY created_at DESC")
    abstract suspend fun getOrderCartsForUser(userId: Int): List<OrderWithCartItems>
}