package com.example.domain.models

import java.io.Serializable

data class Wishlist(val user: User, val product: Product) : Serializable
