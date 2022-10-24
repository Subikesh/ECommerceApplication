package com.example.data.api

import com.example.data.api.ApiConstants.AFFILIATE_ID
import com.example.data.api.ApiConstants.AFFILIATE_TOKEN
import com.example.data.api.models.ProductsList
import retrofit2.Call
import com.example.data.api.models.CategoryResult
import retrofit2.Response
import retrofit2.http.*

interface ApiDataService {

    /**
     * Get complete category list
     */
    @Headers("Fk-Affiliate-Id: $AFFILIATE_ID", "Fk-Affiliate-Token: $AFFILIATE_TOKEN")
    @GET(ApiConstants.UrlEndpoints.categoriesUrl)
    fun getCategories(): Call<CategoryResult>

    @Headers("Fk-Affiliate-Id: $AFFILIATE_ID", "Fk-Affiliate-Token: $AFFILIATE_TOKEN")
    @GET(ApiConstants.UrlEndpoints.categoriesUrl)
    fun getAllCategories(): Response<CategoryResult>

    /**
     * Gets list of all products from a specific category
     * @param categoryId    category Id for specific category
     * @param expires       URL expiry time
     * @param sig           URL validation signature
     * @param inStock       To filter all products which are in stock
     * @return ProductsList object which contains all the products in the category
     */
    @Headers("Fk-Affiliate-Id: $AFFILIATE_ID", "Fk-Affiliate-Token: $AFFILIATE_TOKEN")
    @GET(ApiConstants.UrlEndpoints.productsListUrl)
    fun getProductsList(
        @Path("category_id") categoryId: String,
        @Query("expiresAt") expires: String,
        @Query("sig") sig: String,
        @Query("inStock") inStock: Int = 1
    ): Call<ProductsList>

    /**
     * Get list of products from the complete URL
     * @param apiUrl    Url of api call
     * @param inStock   Filter all products which are in stock
     */
    @Headers("Fk-Affiliate-Id: $AFFILIATE_ID", "Fk-Affiliate-Token: $AFFILIATE_TOKEN")
    @GET
    fun getProductsList(@Url apiUrl: String, @Query("inStock") inStock: Int = 1): Call<ProductsList>

    @Headers("Fk-Affiliate-Id: $AFFILIATE_ID", "Fk-Affiliate-Token: $AFFILIATE_TOKEN")
    @GET
    suspend fun getProductsFromUrl(@Url apiUrl: String, @Query("inStock") inStock: Int = 1): Response<ProductsList>
}