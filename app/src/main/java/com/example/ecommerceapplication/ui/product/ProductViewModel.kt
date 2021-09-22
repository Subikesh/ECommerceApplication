package com.example.ecommerceapplication.ui.product

import androidx.lifecycle.ViewModel
import com.example.domain.models.Product

class ProductViewModel : ViewModel() {

    private lateinit var product: Product
    var inWishlist: Boolean = false

    internal fun setProduct(_product: Product) { product = _product }

    fun wishlistProduct(): Boolean {
        // TODO: Implement this function after db design
        inWishlist = !inWishlist
        return inWishlist
    }
}