package com.example.domain.mapper

import com.example.domain.models.Category
import com.example.domain.models.Product

/**
 * Maps a api model to our project model
 * @param <T> API model class
 * @param <U> product model class
 */
interface APIModelMapper<T, U> {
    fun fromApiModel(apiModel: T, itemCount: Int = 15): List<U>
}

/**
 * Maps category api model to POJO class
 * @param <T> API model class
 */
interface CategoryApiMapper<T> : APIModelMapper<T, Category> {
    fun fromApiModel(apiModel: T, fromItem: Int, toItem: Int): List<Category>
}

/**
 * Maps product api model to POJO class
 * @param <T> API model class
 */
interface ProductApiMapper<T> : APIModelMapper<T, Product>