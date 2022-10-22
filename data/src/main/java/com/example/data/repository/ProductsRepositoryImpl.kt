package com.example.data.repository

import android.util.Log
import com.example.data.api.ApiDataService
import com.example.data.mapper.ProductApiMapperImpl
import com.example.data.mapper.ProductEntityMapperImpl
import com.example.data.roomdb.dao.ProductDao
import com.example.domain.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(private val apiService: ApiDataService, private val productDao: ProductDao) {
    suspend fun getProducts(productUrl: String, categoryId: String, itemCount: Int): List<Product> = withContext(Dispatchers.IO) {
        val products = apiService.getProductsFromUrl(productUrl)
        val productList = ProductApiMapperImpl.fromApiModel(products.body()!!, categoryId, itemCount)
        //TODO: write products to database
        productDao.insertAll(productList.map {
            ProductEntityMapperImpl.toEntity(it)
        })
        Log.d("Prod", "Products: $products $productList")
        return@withContext productList
    }
}