package org.the_chance.honeymart.ui.features.coupons

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import java.text.SimpleDateFormat
import java.util.Date


//region Ui State
data class CouponsUiState(
    val isLoading: Boolean = false,
    val isConnectionError: Boolean = false,
    val error: ErrorHandler? = null,
    val isDatePickerVisible: Boolean = false,
    val productSearch: ProductSearchUiState = ProductSearchUiState(),
    val addCoupon: AddCouponUiState = AddCouponUiState(),
)

data class ProductSearchUiState(
    val isLoading: Boolean = false,
    val searchText: String = "",
    val products: List<ProductUiState> = emptyList(),
)

data class ProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productImageUrl: String = "",
    val productPrice: Double = 0.0,
) {
    val productPriceFormatted: String = productPrice.toProductPriceFormat()
    val productPriceCouponFormat: String = productPrice.toCouponPriceFormat()
}

data class AddCouponUiState(
    val isLoading: Boolean = false,
    val discountPercentage: String = "",
    val couponCount: String = "",
    val expirationDate: Date? = null,
    val coupon: CouponUiState = CouponUiState(),
    val discountPercentageState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val couponCountState: ValidationState = ValidationState.VALID_TEXT_FIELD,
) {
    val expirationDateFormatted: String =
        expirationDate?.toCouponExpirationDateFormat() ?: "Expiration Date"
}

data class CouponUiState(
    val count: String = "0",
    val offerPrice: String = "$0.0",
    val expirationDate: String = "00.00.0000",
    val product: ProductUiState = ProductUiState(),
)

//endregion

// region Mappers
fun List<Product>.toProductUiState(): List<ProductUiState> {
    return map { product ->
        ProductUiState(
            productId = product.productId,
            productName = product.productName,
            productImageUrl = product.productImages.takeIf { it.isNotEmpty() }?.firstOrNull() ?: "",
            productPrice = product.productPrice,
        )
    }
}
// endregion

// region Extensions
fun Double.toProductPriceFormat(): String {
    val decimalFormat = DecimalFormat("#,##0.0'$'")
    return decimalFormat.format(this)
}

fun Double.toCouponPriceFormat(): String {
    val decimalFormat = DecimalFormat("'$'#,##0.0")
    return decimalFormat.format(this)
}

fun Double.toOfferPrice(discountPercentage: Double): Double {
    return (this - (this * discountPercentage / 100))
}

@SuppressLint("SimpleDateFormat")
fun Date.toCouponExpirationDateFormat(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")
    return dateFormat.format(this)
}

fun CouponsUiState.showContent(): Boolean {
    return !isConnectionError && error == null && !isLoading
}

fun CouponsUiState.showConnectionError(): Boolean {
    return isConnectionError && !isLoading
}

fun AddCouponUiState.showButton(): Boolean {
    return discountPercentage.isNotBlank()
            && couponCount.isNotBlank()
            && expirationDate != null
            && discountPercentageState == ValidationState.VALID_TEXT_FIELD
            && couponCountState == ValidationState.VALID_TEXT_FIELD
}

fun AddCouponUiState.showCoupon(): Boolean {
    return coupon.product.productId != 0L && !isLoading
}

fun AddCouponUiState.showEmptyPlaceHolder(): Boolean {
    return coupon.product.productId == 0L && !isLoading
}

fun ProductSearchUiState.showEmptyPlaceHolder(): Boolean {
    return products.isEmpty() && !isLoading
}
// endregion