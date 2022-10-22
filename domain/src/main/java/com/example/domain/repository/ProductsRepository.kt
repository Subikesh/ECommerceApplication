package com.example.domain.repository

import com.example.domain.models.Product

interface ProductsRepository {
    suspend fun getProducts(productUrl: String, categoryId: String, itemCount: Int): Result<List<Product>>
}