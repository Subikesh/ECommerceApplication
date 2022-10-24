package com.example.domain.repository

import com.example.domain.models.Category

interface CategoryRepository {
    suspend fun fetchAndSaveCategories(forceReload: Boolean): Result<List<Category>>
}