package com.example.data.usecases

import android.content.Context
import com.example.data.repository.CategoryEntityMapperImpl
import com.example.data.repository.ProductEntityMapperImpl
import com.example.data.roomdb.DatabaseContract
import com.example.data.roomdb.entities.MutablePair
import com.example.domain.models.Category
import com.example.domain.models.Product

class CategoryDatabase(context: Context) {
    /** Database object to access database */
    private val db = DatabaseContract.getInstance(context)

    suspend fun getCategory(categoryId: String) = db.categoryDao().get(categoryId)

    suspend fun insertCategories(categoryPairList: List<MutablePair<Category, List<Product>?>>) {
        for (categoryPair in categoryPairList) {
            insertCategoryPair(categoryPair)
        }
    }

    suspend fun insertCategory(category: Category) {
        val categoryEntity = CategoryEntityMapperImpl.toEntity(category)
        db.categoryDao().insert(categoryEntity)
    }

//    suspend fun searchCategory(search: String) = db.categoryDao().searchCategories(search.lowercase())

    /**
     * Runs a search query with the search string on all products
     * @param search the search query
     * @return list of products searched
     */
    suspend fun searchProducts(search: String) =
        db.productDao().searchProducts(search.lowercase()).map {
            ProductEntityMapperImpl.fromEntity(it)
        }

    /**
     * Insert categories and corresponding products to database
     * @param categoryPair Pair of Category to List of products
     */
    suspend fun insertCategoryPair(categoryPair: MutablePair<Category, List<Product>?>) {
        val category = CategoryEntityMapperImpl.toEntity(categoryPair.first)
        db.categoryDao().insert(category)
        if (!(categoryPair.second.isNullOrEmpty())) {
            for (productModel in categoryPair.second!!) {
                val product = ProductEntityMapperImpl.toEntity(productModel)
                db.productDao().insert(product)
            }
        }
    }
}