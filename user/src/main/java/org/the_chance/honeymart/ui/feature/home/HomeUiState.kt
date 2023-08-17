package org.the_chance.honeymart.ui.feature.home

import org.the_chance.honeymart.domain.model.CouponEntity
import org.the_chance.honeymart.domain.model.GetRecentProductsEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.category.CategoryUiState
import org.the_chance.honeymart.ui.feature.market.MarketUiState
import org.the_chance.honeymart.ui.feature.orders.OrderStates
import org.the_chance.honeymart.ui.feature.orders.OrderUiState
import org.the_chance.honeymart.ui.feature.product.ProductUiState

data class HomeUiState(
    val searchClick: Boolean = false,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val categories: List<CategoryUiState> = emptyList(),
    val markets: List<MarketUiState> = emptyList(),
    val coupons: List<CouponUiState> = emptyList(),
    val newProducts: List<NewProductUiState> = emptyList(),
    val lastPurchases: List<OrderUiState> = emptyList(),
    val discoverProducts: List<ProductUiState> = emptyList(),
    val orderStates: OrderStates = OrderStates.DONE,
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
    val isFavorite: Boolean = false
)

fun CouponEntity.toCouponUiState() = CouponUiState(
    couponId = couponId,
    count = count,
    discountPercentage = discountPercentage,
    expirationDate = expirationDate,
    product = product,
    isClipped = isClipped,
)

fun GetRecentProductsEntity.toGetRecentProductUiState() = NewProductUiState(
    newProductId = productId,
    newProductName = productName,
    newProductImage = productImages[0],
    price = ProductPrice,
    isFavorite = false,
)