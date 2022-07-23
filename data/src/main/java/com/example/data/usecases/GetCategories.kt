package com.example.data.usecases

import androidx.lifecycle.MutableLiveData
import com.example.data.api.GetApiDataService
import com.example.data.api.RetrofitInstance
import com.example.data.api.models.CategoryResult
import com.example.data.mapper.CategoryApiMapperImpl
import com.example.domain.models.Category
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class GetCategories @Inject constructor() {
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
                categoryList = CategoryApiMapperImpl.fromApiModel(categoryObjects)
                allCategories.value = categoryList
            }

            override fun onFailure(call: Call<CategoryResult>, t: Throwable) {
                allCategories.postValue(null)
            }
        })
        return allCategories
    }

    /**
     * Loads more categories to list and appends to allCategories
     * @param categoryCount number of categories to be loaded
     * @return complete list of categories that was loaded
     */
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