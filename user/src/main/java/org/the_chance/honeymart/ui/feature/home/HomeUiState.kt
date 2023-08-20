package org.the_chance.honeymart.ui.feature.home

import org.the_chance.honeymart.domain.model.CouponEntity
import org.the_chance.honeymart.domain.model.RecentProductEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.model.ValidCouponEntity
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.category.CategoryUiState
import org.the_chance.honeymart.ui.feature.market.MarketUiState
import org.the_chance.honeymart.ui.feature.orders.OrderUiState
import org.the_chance.honeymart.ui.feature.product.ProductUiState

data class HomeUiState(
    val isLoading: Boolean = true,
    val isConnectionError: Boolean = false,
    val error: ErrorHandler? = null,
    val selectedMarketId: Long = 0L,
    val categories: List<CategoryUiState> = emptyList(),
    val markets: List<MarketUiState> = emptyList(),
    val coupons: List<CouponUiState> = emptyList(),
    val validCoupons: List<ValidCouponUiState> = emptyList(),
    val recentProducts: List<RecentProductUiState> = emptyList(),
    val lastPurchases: List<OrderUiState> = emptyList(),
    val discoverProducts: List<ProductUiState> = emptyList(),
)


data class CouponUiState(
    val couponId: Long,
    val count: Int,
    val discountPercentage: Double,
    val expirationDate: String,
    val product: ProductEntity,
    val isClipped: Boolean,
)

data class RecentProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productImage: String = "",
    val price: Double = 0.0,
    val isFavorite: Boolean = false
)

data class ValidCouponUiState(
    val couponId: Long,
    val count: Int,
    val discountPercentage: Double,
    val expirationDate: String,
    val product: ProductEntity,
)

fun ValidCouponEntity.toValidCouponUiState() = ValidCouponUiState(
    couponId = couponId,
    count = count,
    discountPercentage = discountPercentage,
    expirationDate = expirationDate,
    product = product,
)

fun CouponEntity.toCouponUiState() = CouponUiState(
    couponId = couponId,
    count = count,
    discountPercentage = discountPercentage,
    expirationDate = expirationDate,
    product = product,
    isClipped = isClipped,
)

fun RecentProductEntity.toRecentProductUiState() = RecentProductUiState(
    productId = productId,
    productName = productName,
    productImage = productImages[0],
    price = productPrice,
    isFavorite = false,
)

fun HomeUiState.showHome() = (!this.isLoading) && !isConnectionError
