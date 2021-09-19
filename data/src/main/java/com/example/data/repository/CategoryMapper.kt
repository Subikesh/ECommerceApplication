package com.example.data.repository

import android.util.Log
import com.example.data.api.models.API_VERSION
import com.example.data.api.models.CategoryResult
import com.example.domain.models.Category

object CategoryMapper {
    fun fromApiModel(apiModel: CategoryResult, categoryCount: Int = 15): List<Category> {
        val categoryObjects = apiModel.apiGroups.affiliate.categoryObj
        val categoryList = mutableListOf<Category>()
        var totalCategories = categoryCount
        for (categoryTitle in categoryObjects.keys) {
            if (totalCategories-- > 0) {
            Log.d("API response", "${categoryObjects[categoryTitle]} Hello")
                categoryObjects[categoryTitle]?.versions?.get(API_VERSION)?.let {
                    val cat = Category(
                        it.categoryId,
                        it.title,
                        it.productsUrl
                    )
                    Log.d("API response", "Category $totalCategories : $cat")
                    categoryList.add(cat)
                }
            } else break
        }
        return categoryList
    }
}