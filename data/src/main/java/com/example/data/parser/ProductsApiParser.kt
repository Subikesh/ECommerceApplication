package com.example.data.parser

import com.example.data.api.models.ProductsList
import com.example.domain.models.Product
import javax.inject.Inject

class ProductsApiParser @Inject constructor() {

    fun getProducts(productsApiModel: ProductsList, categoryId: String): List<Product> =
        buildList {
            for (product in productsApiModel.products) {
                add(
                    Product(
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
                )
            }
        }
}