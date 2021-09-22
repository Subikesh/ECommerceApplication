package com.example.data.usecases

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.data.api.GetApiDataService
import com.example.data.api.RetrofitInstance
import com.example.data.api.models.CategoryResult
import com.example.data.repository.CategoryApiMapper
import com.example.domain.models.Category
import retrofit2.Call
import retrofit2.Response

class GetCategories {
    private val allCategories: MutableLiveData<List<Category>> = MutableLiveData()
    private var categoryList: List<Category> = listOf()

    fun callApi(): MutableLiveData<List<Category>> {
        val service = RetrofitInstance.retrofitInstance?.create(GetApiDataService::class.java)
        val call = service?.getCategories()

        call?.enqueue(object : retrofit2.Callback<CategoryResult> {
            override fun onResponse(
                call: Call<CategoryResult>,
                response: Response<CategoryResult>
            ) {
                val categoryObjects = response.body()!!
                Log.d("API response", "Categories retrieved")
                Log.d("API response", "Home categories: ${response.raw()}")
                categoryList = CategoryApiMapper.fromApiModel(categoryObjects)
                allCategories.value = categoryList
            }

            override fun onFailure(call: Call<CategoryResult>, t: Throwable) {
                allCategories.postValue(null)
            }
        })
        return allCategories
    }
}