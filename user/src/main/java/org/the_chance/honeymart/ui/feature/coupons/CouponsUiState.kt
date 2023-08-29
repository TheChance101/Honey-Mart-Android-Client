package org.the_chance.honeymart.ui.feature.coupons

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.home.formatCurrencyWithNearestFraction
import org.the_chance.honeymart.ui.feature.product.ProductUiState
import java.time.LocalDate

data class CouponsUiState(
    val updatedCoupons: List<CouponUiState> = emptyList(),
    val coupons: List<CouponUiState> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val couponsState: CouponsState = CouponsState.ALL,
)

data class CouponUiState(
    val couponId: Long = 0L,
    val count: Int = 0,
    val discountPrice: Double = 0.0,
    val expirationDate: String = "",
    val product: ProductUiState = ProductUiState(),
    val isClipped: Boolean = false,
) {
    val isExpired: Boolean =
        expirationDate.formatDate() <= LocalDate.now().toString()

    val couponProductPrice= product.productPrice.formatCurrencyWithNearestFraction()
    val couponDiscountProductPrice= product.productPrice.discountedPrice(discountPercentage = discountPrice).formatCurrencyWithNearestFraction()
    val imageUrl= product.productImages.takeIf { it.isNotEmpty() }?.firstOrNull() ?: ""

}

enum class CouponsState(val state: Int) {
    ALL(1),
    VALID(2),
    EXPIRED(3),
}

fun String.formatDate(): String {
    val date = this
    val year = date.substring(0, 4)
    val month = date.substring(5, 7)
    val day = date.substring(8, 10)
    return "$day.$month.$year"
}

fun CouponsUiState.all() = this.couponsState == CouponsState.ALL
fun CouponsUiState.valid() = this.couponsState == CouponsState.VALID
fun CouponsUiState.expired() = this.couponsState == CouponsState.EXPIRED

fun CouponsUiState.showCouponsContent() = this.coupons.isNotEmpty() && this.updatedCoupons.isNotEmpty() && !this.isError
fun Double.discountedPrice(discountPercentage: Double): Double {
    return this - (this * discountPercentage / 100)
}