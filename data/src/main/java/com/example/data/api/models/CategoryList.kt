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
    /**
     * Finds the category id from productsUrl which will be of form ../category-id.json/ using regex
     * @return category id retrieved from product url
     */
    val categoryId: String
        get() {
            val regex = """/category/([A-Za-z0-9\-]+)\.json""".toRegex()
            return regex.find(productsUrl)?.groupValues?.get(1).toString()
        }
}