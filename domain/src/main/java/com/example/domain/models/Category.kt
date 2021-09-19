package com.example.domain.models

import com.example.domain.extensions.fromSlug

data class Category(
    val categoryId: String,
    val title_slug: String,
    val productsUrl: String
) {
    val categoryTitle: String get() = title_slug.fromSlug()
}