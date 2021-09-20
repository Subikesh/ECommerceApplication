package com.example.data.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

const val API_VERSION = "v1.1.0"

data class CategoryResult(
    val title: String,
    val description: String,
    val apiGroups: ApiGroups
)

data class ApiGroups(val affiliate: CategoryList)

data class CategoryList(
    val name: String,
    @SerializedName("apiListings") val categoryObj: HashMap<String, AvailableVariants>
)

data class AvailableVariants(
    @SerializedName("availableVariants") val versions: HashMap<String, CategoryDetails>,
    @SerializedName("apiName") val categoryName: String
)

data class CategoryDetails(
    @SerializedName("resourceName") @Expose val title: String,
    @SerializedName("get") @Expose val productsUrl: String
) {
    val categoryId: String get() = getIdFromProductUrl(productsUrl)

    companion object {
        fun getIdFromProductUrl(productsUrl: String): String {
            val regex = """.*/category/([A-Za-z\-])\.json.*""".toRegex()
            return regex.find(productsUrl).toString()
        }
    }
}