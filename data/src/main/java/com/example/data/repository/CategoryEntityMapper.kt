package com.example.data.repository

import com.example.data.roomdb.entities.Category
import com.example.domain.repository.CategoryEntityMapper

object CategoryEntityMapperImpl : CategoryEntityMapper<Category> {
    override fun fromEntity(entity: Category) = com.example.domain.models.Category(
        entity.categoryId,
        entity.title_slug,
        entity.productsUrl
    )

    override fun toEntity(model: com.example.domain.models.Category) = Category(
        model.categoryId,
        model.title_slug,
        model.categoryTitle,
        model.productsUrl,
    )
}