package com.example.ecommerceapplication.util

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/** Contains constants regrading toast durations */
sealed class ToastDuration(val value: Int) {
    object SHORT : ToastDuration(Toast.LENGTH_SHORT)
    object LONG : ToastDuration(Toast.LENGTH_LONG)
}

/** Util functions regarding toasts */
class ToastUtil @Inject constructor(@ApplicationContext private val context: Context) {
    fun displayToast(message: String, duration: ToastDuration) {
        Toast.makeText(context, message, duration.value).show()
    }
}