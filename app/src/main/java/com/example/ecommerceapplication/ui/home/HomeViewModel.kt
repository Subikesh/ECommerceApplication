package com.example.ecommerceapplication.ui.home

import androidx.lifecycle.ViewModel
import com.example.data.usecases.GetCategories
import com.example.data.usecases.GetProducts
import com.example.domain.models.Category
import com.example.domain.models.Product

// Mutable pair class
data class Pair<A, B>(var first: A, var second: B)

class HomeViewModel : ViewModel() {
    private var categoryApi = GetCategories()
    private val productsApi = GetProducts()


    var categoryList: MutableList<Pair<Category, List<Product>?>>? = null

    fun loadCategories() = categoryApi.callApi()

    fun loadMoreCategories(loadMoreCount: Int) = categoryApi.loadMoreCategories(loadMoreCount)

    fun loadProducts(productsUrl: String, categoryId: String, itemCount: Int = 10) = productsApi.callApi(productsUrl, categoryId, itemCount)
}