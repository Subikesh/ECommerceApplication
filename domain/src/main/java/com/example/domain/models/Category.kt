package com.example.domain.models

import com.example.domain.extensions.fromSlug
import java.io.Serializable

data class Category(
    val categoryId: String,
    val title_slug: String,
    val productsUrl: String
) : Serializable {
    val categoryTitle: String get() = title_slug.fromSlug()
}