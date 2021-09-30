package com.example.ecommerceapplication.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.CategoryEntityMapperImpl
import com.example.data.repository.ProductEntityMapperImpl
import com.example.data.usecases.CategoryDatabase
import com.example.data.usecases.GetCategories
import com.example.data.usecases.GetProducts
import com.example.domain.models.Category
import com.example.domain.models.Product
import kotlinx.coroutines.launch
import com.example.data.roomdb.entities.MutablePair
import java.util.*

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

    suspend fun searchCategories(search: String): MutableList<MutablePair<Category, MutableList<Product>?>> {
        val categoryList = mutableListOf<MutablePair<Category, MutableList<Product>?>>()
        val categories = categoryDatabase.searchCategory(search)
        for (categoryPair in categories) {
            val tempProducts: MutableList<Product> = mutableListOf()
            for (product in categoryPair.products)
                tempProducts.add(ProductEntityMapperImpl.fromEntity(product))
            categoryList.add(
                MutablePair(
                    CategoryEntityMapperImpl.fromEntity(categoryPair.category),
                    tempProducts
                )
            )
        }
        return categoryList
    }

    suspend fun searchProducts(search: String): SortedMap<String, MutableList<Product>> {
        val products = categoryDatabase.searchProducts(search)
        val productsMap = hashMapOf<String, MutableList<Product>>().apply {
            for (product in products) {
                if (get(product.categoryId) == null) {
                    set(
                        product.categoryId,
                        mutableListOf(ProductEntityMapperImpl.fromEntity(product))
                    )
                } else {
                    this[product.categoryId]?.add(ProductEntityMapperImpl.fromEntity(product))
                }
            }
        }
        return productsMap.toSortedMap(compareBy { productsMap[it]?.size })
    }

    suspend fun searchQuery(search: String): List<MutablePair<Category, List<Product>?>> {
        // Search in categories
        val searchList = searchCategories(search)

        // Search in products
        val productSorted = searchProducts(search)

        // If products searched are merging with categories searched
        for (category in searchList) {
            if (productSorted[category.first.categoryId] != null) {
                productSorted[category.first.categoryId]!!.addAll(category.second!!)
                category.second = productSorted[category.first.categoryId]
                productSorted.remove(category.first.categoryId)
            }
        }

        // Adding remaining searched products to searchList
        for (productMap in productSorted) {
            viewModelScope.launch {
                searchList.add(
                    MutablePair(
                        CategoryEntityMapperImpl.fromEntity(
                            categoryDatabase.getCategory(productMap.key)
                        ), productMap.value
                    )
                )
            }
        }

        // Type conversion of searchList from MutableList to List
        val resultList = mutableListOf<MutablePair<Category, List<Product>?>>().apply {
            for (category in searchList)
                add(MutablePair(category.first, category.second?.toList()))
        }

        return resultList.toList()
    }
}