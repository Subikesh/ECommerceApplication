package com.example.ecommerceapplication.ui.home

import androidx.lifecycle.ViewModel
import com.example.data.usecases.GetCategories
import com.example.data.usecases.GetProducts


class HomeViewModel : ViewModel() {
    private var categoryApi = GetCategories()
    private val productsApi = GetProducts()

    fun loadCategories() = categoryApi.callApi()

    fun loadProducts(productsUrl: String, categoryId: String, itemCount: Int = 10) = productsApi.callApi(productsUrl, categoryId, itemCount)
}