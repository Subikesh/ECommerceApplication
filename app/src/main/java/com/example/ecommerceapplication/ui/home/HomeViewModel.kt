package com.example.ecommerceapplication.ui.home

import androidx.lifecycle.ViewModel
import com.example.data.repository.ApiRepository


class HomeViewModel() : ViewModel() {
    private var repository: ApiRepository = ApiRepository()

    fun loadCategories() = repository.callApi()
}