package com.example.data.repository

import com.example.data.api.models.ProductsList
import com.example.domain.models.Product
import com.example.domain.models.Price
import com.example.domain.repository.ProductApiMapper

object ProductMapper : ProductApiMapper<ProductsList> {
    override fun fromApiModel(apiModel: ProductsList, itemCount: Int): List<Product> {
        val productObjects = mutableListOf<Product>()
        var totalProducts = itemCount
        for (product in apiModel.products) {
            if (totalProducts-- > 0) {
                val newProduct = Product(
                    product.productBaseInfo.productId,
                    product.productBaseInfo.title,
                    product.productBaseInfo.imageUrls.`200x200`,
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