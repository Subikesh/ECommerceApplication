package com.example.ecommerceapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.roomdb.entities.MutablePair
import com.example.data.usecases.CategoryDatabase
import com.example.data.usecases.GetCategories
import com.example.data.usecases.GetProducts
import com.example.domain.models.Category
import com.example.domain.models.Product
import kotlinx.coroutines.launch

class HomeViewModel(
    private val categoryApi: GetCategories,
    private val productsApi: GetProducts,
    private val categoryDatabase: CategoryDatabase
) : ViewModel() {

    var categoryList: MutableList<MutablePair<Category, List<Product>?>>? = null

    fun loadCategories() = categoryApi.callApi()

    fun loadMoreCategories(loadMoreCount: Int) = categoryApi.loadMoreCategories(loadMoreCount)

    fun loadProducts(productsUrl: String, categoryId: String, itemCount: Int = 10) =
        productsApi.callApi(productsUrl, categoryId, itemCount)

    /**
     * Loads the category and products from categoryPair to database
     * @param categoryPair MutablePair of category and List of products
     */
    fun loadCategoryDatabase(categoryPair: MutablePair<Category, List<Product>?>) {
        viewModelScope.launch {
            categoryDatabase.insertCategoryPair(categoryPair)
        }
    }

    suspend fun searchProducts(search: String) = categoryDatabase.searchProducts(search)

    class Factory constructor(
        private val categoryApi: GetCategories,
        private val productsApi: GetProducts,
        private val categoryDatabase: CategoryDatabase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(categoryApi, productsApi, categoryDatabase) as T
        }
    }
}