package org.the_chance.honeymart.ui.feature.coupons

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import org.the_chance.honeymart.domain.model.Coupon
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.product.ProductUiState
import org.the_chance.honeymart.ui.feature.product.toProductUiState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

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
    val expirationDate: Date = Date(),
    val product: ProductUiState = ProductUiState(),
    val isClipped: Boolean = false,
) {
    val expirationDateFormat = expirationDate.toCouponExpirationDateFormat()
    val discountPriceInCurrency = discountPrice.formatCurrencyWithNearestFraction()
    val isExpired = expirationDate < Calendar.getInstance().time
    val imageUrl = product.productImages.takeIf { it.isNotEmpty() }?.firstOrNull() ?: ""
}


fun Coupon.toCouponUiState() = CouponUiState(
    couponId = couponId,
    count = count,
    discountPrice = product.productPrice.discountedPrice(discountPercentage = discountPercentage),
    expirationDate = expirationDate,
    product = product.toProductUiState(),
    isClipped = isClipped,
)

enum class CouponsState(val state: Int) {
    ALL(1),
    VALID(2),
    EXPIRED(3),
}

@SuppressLint("SimpleDateFormat")
fun Date.toCouponExpirationDateFormat(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")
    return dateFormat.format(this)
}


fun Double.formatCurrencyWithNearestFraction(): String {
    val decimalFormat = DecimalFormat("'$'#,##0.0")
    return decimalFormat.format(this)
}

fun CouponsUiState.all() = this.couponsState == CouponsState.ALL
fun CouponsUiState.valid() = this.couponsState == CouponsState.VALID
fun CouponsUiState.expired() = this.couponsState == CouponsState.EXPIRED

fun CouponsUiState.showCouponsContent() =
    this.coupons.isNotEmpty() && this.updatedCoupons.isNotEmpty() && !this.isError

fun Double.discountedPrice(discountPercentage: Double): Double {
    return this - (this * discountPercentage / 100)
}