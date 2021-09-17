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

 /*// TODO: Remove this function after testing
fun makeApiCall() {

    val service: GetCategoryDataService = RetrofitInstance.retrofitInstance!!.create(
        GetCategoryDataService::class.java
    )
//    val call: Call<ProductsList> =
//        service.getProductsList("ckf-czl", "1631484625079", "6abc37e38b114548af4aa5f35926dd2f")

    val call: Call<CategoryResult> = service.getCategories()

    Log.d("URL Called URL", call.request().url().toString())
    Log.d("URL Called header", call.request().headers().toString())

    call.enqueue(object : Callback<CategoryResult?> {
        override fun onResponse(call: Call<CategoryResult?>?, response: Response<CategoryResult?>) {
            Log.d("URL Headers", response.headers().toString())
            Log.d("URL Raw", response.raw().toString())
            Log.d(
                "URL response",
                "Categories: ${response.body()?.apiGroups?.affiliate?.categoryObj?.get("food_nutrition")}"
            )
        }

        override fun onFailure(call: Call<CategoryResult?>?, t: Throwable) {
            Log.d("URL Error", "Something went wrong. Error message: " + t.message)
        }
    })
}*/