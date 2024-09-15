package com.example.data.api

import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RetrofitInstance {
    private const val BASE_URL = "https://affiliate-api.flipkart.net/affiliate/"

    /**
     * Create an instance of Retrofit object
     */
    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}