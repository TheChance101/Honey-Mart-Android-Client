package org.the_chance.honeymart.ui.feature.home

import android.icu.text.DecimalFormat
import org.the_chance.honeymart.domain.model.Coupon
import org.the_chance.honeymart.domain.model.Market
import org.the_chance.honeymart.domain.model.RecentProduct
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.category.CategoryUiState
import org.the_chance.honeymart.ui.feature.orders.OrderUiState
import org.the_chance.honeymart.ui.feature.product.ProductUiState
import org.the_chance.honeymart.ui.feature.product.toProductUiState

data class HomeUiState(
    val isLoading: Boolean = true,
    val isConnectionError: Boolean = false,
    val error: ErrorHandler? = null,
    val selectedMarketId: Long = 0L,
    val markets: List<MarketUiState> = emptyList(),
    val categories: List<CategoryUiState> = emptyList(),
    val coupons: List<CouponUiState> = emptyList(),
    val recentProducts: List<RecentProductUiState> = emptyList(),
    val lastPurchases: List<OrderUiState> = emptyList(),
    val discoverProducts: List<ProductUiState> = emptyList(),
)
data class MarketsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val markets: List<MarketUiState> = emptyList(),
)

data class MarketUiState(
    val marketId: Long = 0L,
    val marketName: String = "",
    val marketImage: String = "",
    val isClicked : Boolean = false
)



data class CouponUiState(
    val couponId: Long = 0L,
    val count: Int = 0,
    val discountPrice: Double = 0.0,
    val expirationDate: String = "",
    val product: ProductUiState = ProductUiState(),
    val isClipped: Boolean = false,
)

data class RecentProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productImage: String = "",
    val price: Double = 0.0,
    val isFavorite: Boolean = false
)

fun Coupon.toCouponUiState() = CouponUiState(
    couponId = couponId,
    count = count,
    discountPrice = product.productPrice.discountedPrice(discountPercentage = discountPercentage),
    expirationDate = expirationDate.formatDate(),
    product = product.toProductUiState(),
    isClipped = isClipped,
)

fun RecentProduct.toRecentProductUiState() = RecentProductUiState(
    productId = productId,
    productName = productName,
    productImage = productImages[0],
    price = productPrice,
    isFavorite = false,
)

fun HomeUiState.showHome() = markets.isNotEmpty() && !isConnectionError

fun String.formatDate(): String {
    val date = this
    val year = date.substring(0, 4)
    val month = date.substring(5, 7)
    val day = date.substring(8, 10)
    return "$day.$month.$year"
}

fun Double.formatCurrencyWithNearestFraction(): String {
    val decimalFormat = DecimalFormat("'$'#,##0.0")
    return decimalFormat.format(this)
}

fun Double.discountedPrice(discountPercentage: Double): Double {
    return this - (this * discountPercentage / 100)
}

fun Market.toMarketUiState(): MarketUiState {
    return MarketUiState(
        marketId = marketId,
        marketName = marketName,
        marketImage = imageUrl
    )
}