package com.example.ecommerceapplication.ui.category

import androidx.lifecycle.ViewModel
import com.example.domain.models.Product
import com.example.domain.usecase.GetProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val getCategoryProductsUseCase: GetProductsByCategoryUseCase): ViewModel() {

    suspend fun fetchProducts(productsUrl: String, categoryId: String, itemCount: Int = 10): Result<List<Product>> =
        getCategoryProductsUseCase.execute(GetProductsByCategoryUseCase.ReqParams(productsUrl, categoryId, itemCount))

}