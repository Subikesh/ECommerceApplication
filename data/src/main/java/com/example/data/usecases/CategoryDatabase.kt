package com.example.data.usecases

import android.content.Context
import android.util.Log
import com.example.data.repository.CategoryEntityMapperImpl
import com.example.data.repository.ProductEntityMapperImpl
import com.example.data.roomdb.DatabaseContract
import com.example.domain.models.Category
import com.example.domain.models.Product
import com.example.data.roomdb.entities.MutablePair

class CategoryDatabase(context: Context) {
    /** Database object to access database */
    private val db = DatabaseContract.getInstance(context)

    suspend fun insertCategories(categoryPairList: List<MutablePair<Category, List<Product>?>>) {
        for (categoryPair in categoryPairList) {
            insertCategory(categoryPair)
        }
    }

    suspend fun insertCategory(categoryPair: MutablePair<Category, List<Product>?>) {
        val category = CategoryEntityMapperImpl.toEntity(categoryPair.first)
        db.categoryDao().insert(category)
        Log.d("DatabaseProd", "CategoryProduct: ${categoryPair.second}")
        if (!(categoryPair.second.isNullOrEmpty())) {
            for (productModel in categoryPair.second!!) {
                val product = ProductEntityMapperImpl.toEntity(productModel)
                db.productDao().insert(product)
            }
        }
    }
}