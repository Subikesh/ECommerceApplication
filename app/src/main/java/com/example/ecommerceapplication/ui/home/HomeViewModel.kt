package com.example.ecommerceapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.CategoryDatabase
import com.example.data.repository.GetCategories
import com.example.data.roomdb.entities.MutablePair
import com.example.domain.models.Category
import com.example.domain.models.Product
import com.example.domain.usecase.GetAndSaveCategoriesUseCase
import com.example.domain.usecase.GetProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryApi: GetCategories,
    private val categoryDatabase: CategoryDatabase,
    private val getAndSaveCategoriesUseCase: GetAndSaveCategoriesUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase
) : ViewModel() {

    //Todo: Use search query as this live data
    private val _searchQuery: MutableLiveData<String> = MutableLiveData()
    private val searchQuery: LiveData<String> = _searchQuery

    fun loadMoreCategories(loadMoreCount: Int) = categoryApi.loadMoreCategories(loadMoreCount)

    suspend fun fetchCategories(reload: Boolean = false): Result<List<Category>> {
        return getAndSaveCategoriesUseCase.execute(GetAndSaveCategoriesUseCase.ReqParams(reload))
    }

    suspend fun getTopProductsForCategory(category: Category): Result<List<Product>> {
        return getProductsByCategoryUseCase.execute(
            GetProductsByCategoryUseCase.ReqParams(
                category.productsUrl,
                category.categoryId,
                10
            )
        )
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