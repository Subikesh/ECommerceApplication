package com.example.domain.usecase

import com.example.domain.models.Product
import com.example.domain.repository.ProductsRepository
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val repository: ProductsRepository
) : BaseUseCase<List<Product>, GetProductsByCategoryUseCase.ReqParams>() {

    override suspend fun executeOnBackground(param: ReqParams): Result<List<Product>> {
        return repository.getProducts(param.productUrl, param.categoryId, param.itemCount)
    }

    data class ReqParams(val productUrl: String, val categoryId: String, val itemCount: Int)
}