package com.example.ecommerceapplication.ui.category

import androidx.lifecycle.ViewModel
import com.example.domain.models.Category
import com.example.domain.models.Product
import com.example.domain.usecase.GetProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val getCategoryProductsUseCase: GetProductsByCategoryUseCase): ViewModel() {

    suspend fun fetchProducts(category: Category, itemCount: Int = 10): Result<List<Product>> =
        getCategoryProductsUseCase.execute(GetProductsByCategoryUseCase.ReqParams(category.productsUrl, category.categoryId, itemCount))

}