package com.example.data.usecases

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.data.api.GetApiDataService
import com.example.data.api.RetrofitInstance
import com.example.data.api.models.CategoryResult
import com.example.data.repository.CategoryApiMapperImpl
import com.example.domain.models.Category
import retrofit2.Call
import retrofit2.Response

class GetCategories {
    private val allCategories: MutableLiveData<List<Category>> = MutableLiveData()
    private var categoryList: MutableList<Category> = mutableListOf()

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
                categoryList = CategoryApiMapperImpl.fromApiModel(categoryObjects)
                allCategories.value = categoryList
            }

            override fun onFailure(call: Call<CategoryResult>, t: Throwable) {
                allCategories.postValue(null)
            }
        })
        return allCategories
    }

    fun loadMoreCategories(categoryCount: Int): MutableLiveData<List<Category>> {
        val service = RetrofitInstance.retrofitInstance?.create(GetApiDataService::class.java)
        val call = service?.getCategories()

        call?.enqueue(object : retrofit2.Callback<CategoryResult> {
            override fun onResponse(
                call: Call<CategoryResult>,
                response: Response<CategoryResult>
            ) {
                val categoryObjects = response.body()!!
                categoryList.addAll(CategoryApiMapperImpl.fromApiModel(
                    categoryObjects,
                    categoryList.size,
                    categoryList.size + categoryCount
                ))
                allCategories.value = categoryList
            }

            override fun onFailure(call: Call<CategoryResult>, t: Throwable) {
                allCategories.postValue(null)
            }
        })
        return allCategories
    }
}