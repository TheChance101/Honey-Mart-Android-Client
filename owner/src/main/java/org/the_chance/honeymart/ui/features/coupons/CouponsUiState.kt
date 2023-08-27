package org.the_chance.honeymart.ui.features.coupons

import org.the_chance.honeymart.domain.util.ValidationState
import java.util.Date


data class CouponsUiState(
    val isLoading: Boolean = false,
    val productSearch: ProductSearchUiState = ProductSearchUiState(),
    val addCoupon: AddCouponUiState = AddCouponUiState(),
)

data class ProductSearchUiState(
    val selectedProductId: Long = 0L,
    val searchText: String = "",
    val products: List<ProductUiState> = emptyList(),
)

data class ProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productImageUrl: String = "",
    val productPrice: String = "",
)

data class AddCouponUiState(
    val discountPercentage: String = "",
    val couponCount: String = "",
    val showDialog: Boolean = false,
    val expirationDate: Date? = null,
    val coupon: CouponUiState = CouponUiState(),
    val discountPercentageState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val couponCountState: ValidationState = ValidationState.VALID_TEXT_FIELD,
)

data class CouponUiState(
    val couponId: Long = 0L,
    val count: String = "",
    val discountedPrice: String = "",
    val expirationDate: String = "",
    val product: ProductUiState = ProductUiState(),
)