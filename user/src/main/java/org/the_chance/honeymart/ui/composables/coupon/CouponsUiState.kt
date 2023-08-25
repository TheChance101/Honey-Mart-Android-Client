package org.the_chance.honeymart.ui.composables.coupon

import android.icu.text.DecimalFormat
import org.the_chance.honeymart.domain.model.CouponEntity
import org.the_chance.honeymart.ui.feature.product.ProductUiState
import org.the_chance.honeymart.ui.feature.product.toProductUiState
import java.time.LocalDate

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
}

fun CouponEntity.toCouponUiState() = CouponUiState(
    couponId = couponId,
    count = count,
    discountPrice = product.productPrice.discountedPrice(discountPercentage = discountPercentage),
    expirationDate = expirationDate.formatDate(),
    product = product.toProductUiState(),
    isClipped = isClipped,
)


fun String.formatDate(): String {
    val year = this.substring(0, 4)
    val month = this.substring(5, 7)
    val day = this.substring(8, 10)
    return "$year-$month-$day"
}

fun Double.formatCurrencyWithNearestFraction(): String {
    val decimalFormat = DecimalFormat("'$'#,##0.0")
    return decimalFormat.format(this)
}

fun Double.discountedPrice(discountPercentage: Double): Double {
    return this - (this * discountPercentage / 100)
}