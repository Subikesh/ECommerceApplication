package com.example.data.parser

import com.example.data.api.models.API_VERSION
import com.example.data.api.models.CategoryResult
import com.example.domain.models.Category
import javax.inject.Inject

class CategoryApiParser @Inject constructor() {
    fun getCategories(categoryApiModel: CategoryResult): List<Category> {
        val categoryObjects = categoryApiModel.apiGroups.affiliate.categoryObj
        return buildList {
            categoryObjects.values.map { category ->
                val categoryDetails = category.versions[API_VERSION]
                categoryDetails?.let {
                    add(Category(it.categoryId, it.title, it.productsUrl))
                }
            }
        }
    }
}