package com.example.data.api

import com.example.data.BuildConfig

object ApiConstants {
    const val AFFILIATE_ID = BuildConfig.API_ID
    const val AFFILIATE_TOKEN = BuildConfig.API_TOKEN

    object UrlEndpoints {
        const val categoriesUrl = "api/$AFFILIATE_ID.json"
        const val productsListUrl = "1.0/feeds/amaratasi/category/{category_id}.json"
    }
}