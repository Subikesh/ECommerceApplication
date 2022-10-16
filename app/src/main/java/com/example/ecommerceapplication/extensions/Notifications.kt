package com.example.ecommerceapplication.extensions

import android.app.NotificationChannel
import android.app.NotificationChannel.DEFAULT_CHANNEL_ID
import android.app.NotificationManager
import android.app.NotificationManager.*
import android.content.Context
import android.os.Build

sealed class NotificationChannels(
    val CHANNEL_ID: String = DEFAULT_CHANNEL_ID,
    val name: String,
    val descriptionText: String = "",
    val notificationId: Int = 0,
    private val importance: Int = IMPORTANCE_DEFAULT
) {
    object DefaultChannel : NotificationChannels(name = "Default channel")
    object OrdersChannel :
        NotificationChannels("Orders", "Order notifications", "Notifications to track an order", 1)

    object CartChannel : NotificationChannels(
        "Cart",
        "Cart notifications",
        "Notifications to track cart activities",
        2,
        IMPORTANCE_LOW
    )

    object UserConfigChannel : NotificationChannels(
        "User",
        "User notifications",
        "Notifications on user profile changes",
        3,
        IMPORTANCE_HIGH
    )

    object OffersChannel : NotificationChannels(
        "Offers",
        "Offers notifications",
        "Notifies latest offers and trending products",
        4,
        IMPORTANCE_LOW
    )

    fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

fun createNotificationChannels(context: Context) {
    NotificationChannels.OrdersChannel.createNotificationChannel(context)
    NotificationChannels.CartChannel.createNotificationChannel(context)
    NotificationChannels.UserConfigChannel.createNotificationChannel(context)
    NotificationChannels.OffersChannel.createNotificationChannel(context)
}