package com.example.data.repository.ecom

import com.example.data.api.ApiDataService
import com.example.data.mapper.CategoryEntityMapperImpl
import com.example.data.parser.CategoryApiParser
import com.example.data.repository.base.BaseRepository
import com.example.data.roomdb.dao.CategoryDao
import com.example.domain.models.Category
import com.example.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiDataService,
    private val categoryDao: CategoryDao,
    private val categoryApiParser: CategoryApiParser
) : CategoryRepository, BaseRepository() {
    override suspend fun fetchAndSaveCategories(forceReload: Boolean): Result<List<Category>> {
        if (!forceReload) {
            getCategoriesFromDb()?.let {
                return Result.success(it)
            }
        }
        val categoriesResponse = executeNetworkService {
            apiService.getAllCategories()
        }
        return if (categoriesResponse.isSuccess) {
            val categoryList = categoryApiParser.getCategories(categoriesResponse.getOrThrow())
            categoryDao.insertAll(categoryList.map { CategoryEntityMapperImpl.toEntity(it) })
            Result.success(categoryList)
        } else {
            Result.failure(categoriesResponse.exceptionOrNull()!!)
        }
    }

    suspend fun getCategoriesFromDb(): List<Category>? {
        val categoryDbResult = categoryDao.getAll()
        if (categoryDbResult.isNotEmpty()) {
            return categoryDbResult.map { CategoryEntityMapperImpl.fromEntity(it) }
        }
        return null
    }
}