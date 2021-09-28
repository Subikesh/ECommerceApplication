package com.example.data.repository

import android.util.Log
import com.example.data.api.models.API_VERSION
import com.example.data.api.models.CategoryResult
import com.example.domain.models.Category
import com.example.domain.repository.CategoryApiMapper
import kotlin.math.min

object CategoryApiMapperImpl :
    CategoryApiMapper<CategoryResult> {
    override fun fromApiModel(apiModel: CategoryResult, itemCount: Int) =
        fromApiModel(apiModel, 0, itemCount)

    override fun fromApiModel(apiModel: CategoryResult, fromItem: Int, toItem: Int): MutableList<Category> {
        val categoryObjects = apiModel.apiGroups.affiliate.categoryObj
        val categoryList = mutableListOf<Category>()
        for (categoryTitle in categoryObjects.keys.toList().subList(fromItem, min(toItem, categoryObjects.size))) {
            categoryObjects[categoryTitle]?.versions?.get(API_VERSION)?.let {
                val cat = Category(it.categoryId, it.title, it.productsUrl)
                categoryList.add(cat)
            }
        }
        return categoryList
    }
}