package org.the_chance.honeymart.ui.feature.coupons

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.composables.coupon.CouponUiState

data class CouponsUiState(
    val updatedCoupons: List<CouponUiState> = emptyList(),
    val coupons: List<CouponUiState> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val couponsState: CouponsState = CouponsState.ALL,
)

enum class CouponsState(val state: Int) {
    ALL(1),
    VALID(2),
    EXPIRED(3),
}

fun CouponsUiState.all() = this.couponsState == CouponsState.ALL
fun CouponsUiState.valid() = this.couponsState == CouponsState.VALID
fun CouponsUiState.expired() = this.couponsState == CouponsState.EXPIRED

fun CouponsUiState.showCouponsContent() = this.coupons.isNotEmpty() && !this.isError
