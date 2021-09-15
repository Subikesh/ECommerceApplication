package com.example.data.api

import android.util.Log
import com.example.data.api.models.ProductsList
import retrofit2.Call
import com.example.data.api.RetrofitInstance.retrofitInstance
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

const val AFFILIATE_ID = "amaratasi"
const val AFFILIATE_TOKEN = "34c929c04f054f20ad7143ac7dad8b26"

interface GetCategoryDataService {
    /**
     * Gets list of all products from a specific category
     * @param categoryId    category Id for specific category
     * @param expires       URL expiry time
     * @param sig           URL validation signature
     * @param inStock       To filter all products which are in stock
     * @return ProductsList object which contains all the products as list
     */
    @Headers("Fk-Affiliate-Id: $AFFILIATE_ID", "Fk-Affiliate-Token: $AFFILIATE_TOKEN")
    @GET("{category_id}.json")
    fun getProductsList(
        @Path("category_id") categoryId: String,
        @Query("expiresAt") expires: String,
        @Query("sig") sig: String,
        @Query("inStock") inStock: Int = 1
    ): Call<ProductsList>

    @Headers("Fk-Affiliate-Id: $AFFILIATE_ID", "Fk-Affiliate-Token: $AFFILIATE_TOKEN")
    @GET
    fun getProductsList(@Url apiUrl: String, @Query("inStock") inStock: Int = 1): Call<ProductsList>
}

fun makeApiCall() {
    /** Create retrofit instance used to call interface methods */
    val service: GetCategoryDataService = retrofitInstance!!.create(
        GetCategoryDataService::class.java
    )
//    val call: Call<ProductsList> =
//        service.getProductsList("ckf-czl", "1631484625079", "6abc37e38b114548af4aa5f35926dd2f")

    val call: Call<ProductsList> =
        service.getProductsList("https://affiliate-api.flipkart.net/affiliate/1.0/feeds/amaratasi/category/7jv.json?expiresAt=1631636817845&sig=14860c3a1e52510ac8eb1aee1adf53df")

    /**Log the URL called */
    Log.d("URL Called URL", call.request().url().toString())
    Log.d("URL Called header", call.request().headers().toString())

    call.enqueue(object : Callback<ProductsList?> {
        override fun onResponse(call: Call<ProductsList?>?, response: Response<ProductsList?>) {
            Log.d("URL Headers", response.headers().toString())
            Log.d("URL Raw", response.raw().toString())
            Log.d("URL response", "ExpiresAt: ${response.body()?.validTill}")
        }

        override fun onFailure(call: Call<ProductsList?>?, t: Throwable) {
            Log.d("URL Error", "Something went wrong. Error message: " + t.message)
        }
    })
}