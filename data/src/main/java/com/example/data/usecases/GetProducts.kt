package com.example.data.usecases

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.data.api.GetApiDataService
import com.example.data.api.RetrofitInstance
import com.example.data.api.models.ProductsList
import com.example.data.repository.ProductApiMapperImpl
import com.example.domain.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetProducts {
    private val allProducts: MutableLiveData<List<Product>> = MutableLiveData()
    private var productsList: List<Product> = listOf()

    fun callApi(productUrl: String, categoryId: String, itemCount: Int): MutableLiveData<List<Product>> {
        val service = RetrofitInstance.retrofitInstance?.create(GetApiDataService::class.java)
        val call = service?.getProductsList(productUrl)

        call?.enqueue(object : Callback<ProductsList> {
            override fun onResponse(
                call: Call<ProductsList>,
                response: Response<ProductsList>
            ) {
                val productLists = response.body()!!
                productLists.setCategory(categoryId)
                productsList = ProductApiMapperImpl.fromApiModel(productLists, itemCount)
                allProducts.value = productsList
            }

            override fun onFailure(call: Call<ProductsList>, t: Throwable) {
                allProducts.postValue(null)
            }
        })
        return allProducts
    }
}