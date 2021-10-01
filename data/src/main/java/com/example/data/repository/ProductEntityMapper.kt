package com.example.data.repository

import com.example.data.roomdb.entities.Product
import com.example.domain.models.Price
import com.example.domain.repository.ProductEntityMapper

object ProductEntityMapperImpl : ProductEntityMapper<Product> {
    override fun fromEntity(entity: Product) = com.example.domain.models.Product(
        entity.productId,
        entity.title,
        entity.categoryId,
        entity.imageUrl,
        entity.bigImageUrl,
        Price(entity.maximumRetailPrice, entity.currency),
        Price(entity.discountPrice, entity.currency),
        entity.description,
        entity.brand,
        entity.codAvailable,
        entity.productUrl,
        entity.inStock
    )

    override fun toEntity(model: com.example.domain.models.Product) = Product(
        model.productId,
        model.title,
        model.categoryId,
        (model.imageUrl) ?: "",
        (model.bigImageUrl) ?: "",
        model.maximumRetailPrice.amount,
        model.discountPrice.amount,
        model.maximumRetailPrice.currency,
        model.description,
        model.brand,
        model.codAvailable,
        model.productUrl,
        model.inStock,
        model.discountPercent
    )
}