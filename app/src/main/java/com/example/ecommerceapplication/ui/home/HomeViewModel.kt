package com.example.ecommerceapplication.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.data.roomdb.entities.MutablePair
import com.example.data.repository.CategoryDatabase
import com.example.data.repository.GetCategories
import com.example.data.repository.GetProducts
import com.example.domain.models.Category
import com.example.domain.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryApi: GetCategories,
    private val productsApi: GetProducts,
    private val categoryDatabase: CategoryDatabase
) : ViewModel() {

    var categoryList: MutableList<MutablePair<Category, List<Product>?>>? = null

    //Todo: Use search query as this live data
    private val _searchQuery: MutableLiveData<String> = MutableLiveData()
    private val searchQuery: LiveData<String> = _searchQuery

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
}