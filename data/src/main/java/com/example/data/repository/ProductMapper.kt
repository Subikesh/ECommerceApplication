package com.example.data.repository

import android.util.Log
import com.example.data.api.models.ProductsList
import com.example.domain.models.Product
import com.example.domain.models.Price
import com.example.domain.repository.ProductApiMapper

object ProductMapper : ProductApiMapper<ProductsList> {
    /**
     * Retrieves itemCount number of products from api model
     * @param apiModel Model returned from api call
     * @param itemCount Number of products to be retrieved
     * @return list of product model objects
     */
    override fun fromApiModel(apiModel: ProductsList, itemCount: Int): List<Product> {
        val productObjects = mutableListOf<Product>()
        var totalProducts = itemCount
        val categoryId = apiModel.categoryId
        for (product in apiModel.products) {
            if (totalProducts-- > 0) {
                val newProduct = Product(
                    product.productBaseInfo.productId,
                    product.productBaseInfo.title,
                    categoryId,
                    product.productBaseInfo.imageUrls.`200x200`,
                    product.productBaseInfo.imageUrls.`400x400`,
                    Price(
                        product.productBaseInfo.maximumRetailPrice.amount,
                        product.productBaseInfo.maximumRetailPrice.currency
                    ),
                    Price(
                        product.productBaseInfo.flipkartSpecialPrice.amount,
                        product.productBaseInfo.flipkartSpecialPrice.currency
                    ),
                    product.productBaseInfo.productDescription,
                    product.productBaseInfo.productBrand,
                    product.productBaseInfo.codAvailable,
                    product.productBaseInfo.productUrl,
                    product.productBaseInfo.inStock
                )
                productObjects.add(newProduct)
            } else break
        }
        return productObjects
    }
}