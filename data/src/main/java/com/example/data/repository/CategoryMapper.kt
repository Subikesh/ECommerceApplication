package com.example.data.repository

import android.util.Log
import com.example.data.api.models.API_VERSION
import com.example.data.api.models.CategoryResult
import com.example.domain.models.Category
import com.example.domain.repository.CategoryApiMapper

object CategoryMapper : CategoryApiMapper<CategoryResult> {
    override fun fromApiModel(apiModel: CategoryResult, itemCount: Int): List<Category> {
        val categoryObjects = apiModel.apiGroups.affiliate.categoryObj
        val categoryList = mutableListOf<Category>()
        var totalCategories = itemCount
        for (categoryTitle in categoryObjects.keys) {
            if (totalCategories-- > 0) {
                categoryObjects[categoryTitle]?.versions?.get(API_VERSION)?.let {
                    val cat = Category(it.categoryId, it.title, it.productsUrl)
                    categoryList.add(cat)
                }
            } else break
        }
        return categoryList
    }
}