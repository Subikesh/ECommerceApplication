package com.example.data.mapper

import com.example.data.api.models.ProductsList
import com.example.domain.models.Product
import com.example.domain.repository.ProductApiMapper

object ProductApiMapperImpl : ProductApiMapper<ProductsList> {
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
                    product.productBaseInfo.maximumRetailPrice,
                    product.productBaseInfo.flipkartSpecialPrice,
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