package com.example.ecommerceapplication.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ProductEntityMapperImpl
import com.example.data.usecases.CategoryDatabase
import com.example.data.usecases.GetCategories
import com.example.data.usecases.GetProducts
import com.example.domain.models.Category
import com.example.domain.models.Product
import kotlinx.coroutines.launch
import com.example.data.roomdb.entities.MutablePair

class HomeViewModel(context: Application) : AndroidViewModel(context) {
    private var categoryApi = GetCategories()
    private val productsApi = GetProducts()

    private val categoryDatabase = CategoryDatabase(context)

    var categoryList: MutableList<MutablePair<Category, List<Product>?>>? = null

    fun loadCategories() = categoryApi.callApi()

    fun loadMoreCategories(loadMoreCount: Int) = categoryApi.loadMoreCategories(loadMoreCount)

    fun loadProducts(productsUrl: String, categoryId: String, itemCount: Int = 10) =
        productsApi.callApi(productsUrl, categoryId, itemCount)

    /**
     * Loads all the categories and products in categoryList to database
     */
    fun loadCategoriesDatabase() {
        viewModelScope.launch {
            categoryDatabase.insertCategories(categoryList!!.toList())
        }
    }

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
}