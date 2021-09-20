package com.example.domain.repository

import com.example.domain.models.Category
import com.example.domain.models.Product

/**
 * Maps category api model to POJO class
 * @param <T> API model class
 */
interface CategoryApiMapper<T> {
    fun fromApiModel(apiModel: T, itemCount: Int = 15): List<Category>
}

/**
 * Maps product api model to POJO class
 * @param <T> API model class
 */
interface ProductApiMapper<T> {
    fun fromApiModel(apiModel: T, itemCount: Int = 10): List<Product>
}