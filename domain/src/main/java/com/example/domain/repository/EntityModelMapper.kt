package com.example.domain.repository

import com.example.domain.models.Category
import com.example.domain.models.Product

/**
 * Maps an entity from roomDB to Product object
 */
interface ProductEntityMapper<T> {
    fun fromEntity(entity: T): Product
    fun toEntity(product: Product): T
}

/**
 * Maps an entity from roomDB to a Category object
 */
interface CategoryEntityMapper<T> {
    fun fromEntity(entity: T): Category
    fun toEntity(category: Category): T
}
