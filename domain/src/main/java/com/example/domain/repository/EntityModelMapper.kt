package com.example.domain.repository

import com.example.domain.models.Category
import com.example.domain.models.Product
import com.example.domain.models.User

/**
 * Maps an entity from roomDB to and from a model
 * @param <T> Type of entity
 * @param <U> Type of model
 */
interface EntityModelMapper<T, U> {
    fun fromEntity(entity: T): U
    fun toEntity(model: U): T
}

interface UserEntityMapper<T> : EntityModelMapper<T, User>

interface ProductEntityMapper<T> : EntityModelMapper<T, Product>

interface CategoryEntityMapper<T> : EntityModelMapper<T, Category>