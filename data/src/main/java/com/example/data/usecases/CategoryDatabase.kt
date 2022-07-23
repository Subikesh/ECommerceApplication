package com.example.data.usecases

import com.example.data.mapper.CategoryEntityMapperImpl
import com.example.data.mapper.ProductEntityMapperImpl
import com.example.data.roomdb.dao.CategoryDao
import com.example.data.roomdb.dao.ProductDao
import com.example.data.roomdb.entities.MutablePair
import com.example.domain.models.Category
import com.example.domain.models.Product
import javax.inject.Inject

class CategoryDatabase @Inject constructor(private val categoryDao: CategoryDao, private val productDao: ProductDao) {

    suspend fun getCategory(categoryId: String) = categoryDao.get(categoryId)

    suspend fun insertCategories(categoryPairList: List<MutablePair<Category, List<Product>?>>) {
        for (categoryPair in categoryPairList) {
            insertCategoryPair(categoryPair)
        }
    }

    suspend fun insertCategory(category: Category) {
        val categoryEntity = CategoryEntityMapperImpl.toEntity(category)
        categoryDao.insert(categoryEntity)
    }

//    suspend fun searchCategory(search: String) = categoryDao.searchCategories(search.lowercase())

    /**
     * Runs a search query with the search string on all products
     * @param search the search query
     * @return list of products searched
     */
    suspend fun searchProducts(search: String) =
        productDao.searchProducts(search.lowercase()).map {
            ProductEntityMapperImpl.fromEntity(it)
        }

    /**
     * Insert categories and corresponding products to database
     * @param categoryPair Pair of Category to List of products
     */
    suspend fun insertCategoryPair(categoryPair: MutablePair<Category, List<Product>?>) {
        val category = CategoryEntityMapperImpl.toEntity(categoryPair.first)
        categoryDao.insert(category)
        if (!(categoryPair.second.isNullOrEmpty())) {
            for (productModel in categoryPair.second!!) {
                val product = ProductEntityMapperImpl.toEntity(productModel)
                productDao.insert(product)
            }
        }
    }
}