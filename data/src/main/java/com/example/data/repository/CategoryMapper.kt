package com.example.data.repository

import com.example.data.models.Category

class CategoryMapper {
    fun fromEntity(entity: com.example.data.roomdb.entities.Category) = Category(
        entity.categoryId,
        entity.title_slug,
        entity.productsUrl
    )

    fun toEntity(model: Category) = com.example.data.roomdb.entities.Category(model.categoryId, model.title_slug, model.productsUrl)
}