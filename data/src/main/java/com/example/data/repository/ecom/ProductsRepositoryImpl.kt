package com.example.data.repository.ecom

import android.util.Log
import com.example.data.api.ApiDataService
import com.example.data.mapper.ProductEntityMapperImpl
import com.example.data.parser.ProductsApiParser
import com.example.data.repository.base.BaseRepository
import com.example.data.roomdb.dao.ProductDao
import com.example.domain.models.Product
import com.example.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val apiService: ApiDataService,
    private val productsApiParser: ProductsApiParser,
    private val productDao: ProductDao
) : ProductsRepository, BaseRepository() {

    override suspend fun getProducts(productUrl: String, categoryId: String, itemCount: Int): Result<List<Product>> {
            val products = executeNetworkService {
                apiService.getProductsFromUrl(productUrl)
            }
            return if (products.isSuccess) {
                val productList = productsApiParser.getProducts(products.getOrThrow(), categoryId)
                //TODO: write products to database
                productDao.insertAll(productList.map {
                    ProductEntityMapperImpl.toEntity(it)
                })
                Log.i("Result-getProducts", "$productList")
                Result.success(productList)
            } else {
                Result.failure(products.exceptionOrNull()!!)
            }
        }
}