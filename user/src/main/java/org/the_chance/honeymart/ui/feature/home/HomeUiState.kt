package org.the_chance.honeymart.ui.feature.home

import org.the_chance.honeymart.domain.model.CouponEntity
import org.the_chance.honeymart.domain.model.ProductEntity

data class HomeUiState(
    val searchClick: Boolean = false,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: String? = null,
    val categories: List<CategoryUiState> = emptyList(),
    val markets: List<org.the_chance.honeymart.ui.feature.market.MarketUiState> = emptyList(),
    val coupons: List<CouponUiState> = emptyList(),
    val newProducts: List<NewProductUiState> = emptyList(),
    val lastPurchases: List<LastPurchaseUiState> = emptyList(),
    val discoverProducts: List<DiscoverProductUiState> = emptyList(),
)


data class CategoryUiState(
    val categoryId: Long = 0L,
    val categoryName: String = "",
    val categoryImage: String = "",
)


data class CouponUiState(
    val couponId: Long,
    val count: Int,
    val discountPercentage: Double,
    val expirationDate: String,
    val product: ProductEntity,
    val isClipped: Boolean,
    )

data class NewProductUiState(
    val newProductId: Long = 0L,
    val newProductName: String = "",
    val newProductImage: String = "",
    val price: Double = 0.0,
    val isFavorite: Boolean = false,

    )

data class LastPurchaseUiState(
    val lastPurchaseId: Long = 0L,
    val lastPurchaseName: String = "",
    val lastPurchaseImage: String = "",
    val productImages: List<String> = emptyList()
)

data class DiscoverProductUiState(
    val discoverProductId: Long = 0L,
    val discoverProductName: String = "",
    val discoverProductImage: String = "",
    val price: Double = 0.0,
    val isFavorite: Boolean = false,
)


fun CouponEntity.toCouponUiState() = CouponUiState(
    couponId = couponId,
    count = count,
    discountPercentage = discountPercentage,
    expirationDate = expirationDate,
    product = product,
    isClipped = isClipped,
)