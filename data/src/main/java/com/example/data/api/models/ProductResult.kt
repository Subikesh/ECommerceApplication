package com.example.data.api.models

import com.google.gson.annotations.SerializedName

data class ProductResult(
    @SerializedName("categorySpecificInfoV1") val categorySpecificInfo: CategorySpecificInfo,
    @SerializedName("productBaseInfoV1") val productBaseInfo: ProductBaseInfo,
    @SerializedName("productShippingInfoV1") val productShippingInfo: ProductShippingInfo
)

data class CategorySpecificInfo(
    val booksInfo: BooksInfo,
    val detailedSpecs: List<String>,
    val keySpecs: List<String>,
    val lifeStyleInfo: LifeStyleInfo,
    val specificationList: List<Specification>
)

data class ProductBaseInfo(
    val attributes: Attributes,
    val categoryPath: String,
    val codAvailable: Boolean,
    val discountPercentage: Double,
    val flipkartSpecialPrice: Price,
    val imageUrls: ImageUrls,
    val inStock: Boolean,
    val maximumRetailPrice: Price,
    val offers: List<String>,
    val productBrand: String,
    val productDescription: String,
    val productFamily: List<String>,
    val productId: String,
    val productUrl: String,
    val title: String
)

data class ProductShippingInfo(
    val estimatedDeliveryTime: String,
    val sellerAverageRating: Any,
    val sellerName: String,
    val sellerNoOfRatings: Int,
    val sellerNoOfReviews: Int,
    val shippingCharges: Price
)

data class Price(
    val amount: Double,
    val currency: String
)

data class ImageUrls(
    val `200x200`: String,
    val `400x400`: String,
    val `800x800`: String
)