package com.example.data.repository

import com.example.data.models.Product
import com.example.data.roomdb.entities.Price

object ProductMapper {
    fun fromApiModel(apiModel: com.example.data.api.models.Product) = Product(
        apiModel.productBaseInfo.productId,
        apiModel.productBaseInfo.title,
        apiModel.productBaseInfo.imageUrls.`200x200`,
        Price(apiModel.productBaseInfo.maximumRetailPrice.amount, apiModel.productBaseInfo.maximumRetailPrice.currency),
        Price(apiModel.productBaseInfo.flipkartSpecialPrice.amount, apiModel.productBaseInfo.flipkartSpecialPrice.currency),
        apiModel.productBaseInfo.productDescription,
        apiModel.productBaseInfo.productBrand,
        apiModel.productBaseInfo.codAvailable,
        apiModel.productBaseInfo.productUrl,
        apiModel.productBaseInfo.inStock
    )
}