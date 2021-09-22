package com.example.data.repository

import com.example.data.roomdb.entities.Category
import com.example.data.roomdb.entities.Product
import com.example.domain.models.Price
import com.example.domain.repository.CategoryEntityMapper
import com.example.domain.repository.ProductEntityMapper

object ProductEntityMapperImpl : ProductEntityMapper<Product> {
    override fun fromEntity(entity: Product) = com.example.domain.models.Product(
        entity.productId,
        entity.title,
        entity.categoryId,
        entity.imageUrl,
        entity.bigImageUrl,
        entity.maximumRetailPrice,
        entity.discountPrice,
        entity.description,
        entity.brand,
        entity.codAvailable,
        entity.productUrl,
        entity.inStock,
        entity.specifications,
        entity.remarks
    )

    override fun toEntity(product: com.example.domain.models.Product) = Product(
        product.productId,
        product.title,
        product.categoryId,
        product.imageUrl,
        product.bigImageUrl,
        product.maximumRetailPrice,
        product.discountPrice,
        product.description,
        product.brand,
        product.codAvailable,
        product.productUrl,
        product.inStock,
        product.discountPercent,
        product.specifications,
        product.remarks
    )
}

object CategoryEntityMapperImpl : CategoryEntityMapper<Category> {
    override fun fromEntity(entity: Category) = com.example.domain.models.Category(
        entity.categoryId,
        entity.title_slug,
        entity.productsUrl
    )

    override fun toEntity(category: com.example.domain.models.Category) = Category(
        category.categoryId,
        category.title_slug,
        category.categoryTitle,
        category.productsUrl,
    )
}