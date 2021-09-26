package com.example.data.repository

import com.example.data.roomdb.relations.OrderWithCartItems
import com.example.data.roomdb.entities.OrderCartItem

object OrderEntityMapper {
    fun fromEntity(orderEntity: OrderWithCartItems): List<OrderCartItem> {
        val orderCarts = mutableListOf<OrderCartItem>()
        val cartItems = orderEntity.orderItem
        for (cart in cartItems) {
            orderCarts.add(
                OrderCartItem(
                    orderEntity.order.orderId,
                    orderEntity.order.userId,
                    orderEntity.order.total,
                    orderEntity.order.isSuccessful,
                    orderEntity.order.createdAt,
                    cart.quantity,
                    cart.productId
                )
            )
        }
        return orderCarts
    }

    fun fromEntity(orders: List<OrderWithCartItems>) : List<OrderCartItem> {
        val orderCarts = mutableListOf<OrderCartItem>()
        for (order in orders) {
            orderCarts.addAll(fromEntity(order))
        }
        return orderCarts
    }
}