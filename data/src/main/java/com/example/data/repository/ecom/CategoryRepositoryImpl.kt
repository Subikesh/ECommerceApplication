package com.example.data.repository.ecom

import android.util.Log
import com.example.data.api.ApiDataService
import com.example.data.api.NoDataFetchedFromDBException
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
            val dbCategories = getCategoriesWithMinProductsFromDb()
            if (dbCategories.isSuccess && dbCategories.getOrNull()?.isNotEmpty() != null) {
                Log.d("Cat", "Categories list: ${dbCategories.getOrNull()}")
                return dbCategories
            }
        }
        val categoriesResponse = executeNetworkService {
            apiService.getAllCategories()
        }
        return if (categoriesResponse.isSuccess) {
            val categoryList = categoryApiParser.getCategories(categoriesResponse.getOrThrow())
            Log.d("Cat", "Categories: $categoryList")
            categoryDao.insertAll(categoryList.map { CategoryEntityMapperImpl.toEntity(it) })
            getAllCategoriesFromDb()
        } else {

            Result.failure(categoriesResponse.exceptionOrNull()!!)
        }
    }

    private suspend fun getCategoriesWithMinProductsFromDb(minProducts: Int = 1): Result<List<Category>> {
        val categoryDbResult = categoryDao.getCategoriesWithMinProducts(minProducts)
        if (categoryDbResult.isNotEmpty()) {
            return Result.success(categoryDbResult.map { CategoryEntityMapperImpl.fromEntity(it) })
        }
        return Result.failure(NoDataFetchedFromDBException(tableName = "Categories"))
    }

    suspend fun getAllCategoriesFromDb(): Result<List<Category>> {
        val categoryDbResult = categoryDao.getAll()
        if (categoryDbResult.isNotEmpty()) {
            return Result.success(categoryDbResult.map { CategoryEntityMapperImpl.fromEntity(it) })
        }
        return Result.failure(NoDataFetchedFromDBException(tableName = "Categories"))
    }
}