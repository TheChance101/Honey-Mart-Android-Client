package org.the_chance.honeymart.ui.features.coupons

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import arrow.optics.optics
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.util.ErrorHandler
import java.text.SimpleDateFormat
import java.util.Date


//region Ui State
@optics
data class CouponsUiState(
    val isLoading: Boolean = false,
    val isConnectionError: Boolean = false,
    val error: ErrorHandler? = null,
    val isDatePickerVisible: Boolean = false,
    val productSearch: ProductSearchUiState = ProductSearchUiState(),
    val addCoupon: AddCouponUiState = AddCouponUiState(),
) {
    companion object
}

@optics
data class ProductSearchUiState(
    val isLoading: Boolean = false,
    val searchText: String = "",
    val products: List<ProductUiState> = emptyList(),
) {
    companion object
}

@optics
data class ProductUiState(
    val id: Long = 0L,
    val name: String = "",
    val imageUrl: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val isSelected: Boolean = false,
) {
    companion object

    val productPriceFormatted: String = price.toProductPriceFormat()
    val productPriceCouponFormat: String = price.toCouponPriceFormat()
}

@optics
data class AddCouponUiState(
    val isLoading: Boolean = false,
    val discountPercentageState: FieldState = FieldState(),
    val expirationDate: Date? = null,
    val coupon: CouponUiState = CouponUiState(),
    val couponCountState: FieldState = FieldState(),
) {
    companion object

    val expirationDateFormatted: String =
        expirationDate?.toCouponExpirationDateFormat() ?: "Expiration Date"
}

@optics
data class FieldState(
    val name: String = "",
    val errorState: String = "",
    val isValid: Boolean = errorState.isEmpty()
) {
    companion object
}

@optics
data class CouponUiState(
    val count: String = "0",
    val offerPrice: String = "$0.0",
    val expirationDate: String = "00.00.0000",
    val product: ProductUiState = ProductUiState(),
) {
    companion object
}

//endregion

// region Mappers
fun List<Product>.toProductUiState(): List<ProductUiState> {
    return map { product ->
        ProductUiState(
            id = product.productId,
            name = product.productName,
            imageUrl = product.productImages.takeIf { it.isNotEmpty() }?.firstOrNull() ?: "",
            price = product.productPrice,
            description = product.productDescription,
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
    return discountPercentageState.isValid && couponCountState.isValid && expirationDate != null
}

fun AddCouponUiState.showCoupon(): Boolean {
    return coupon.product.id != 0L && !isLoading
}

fun AddCouponUiState.showEmptyPlaceHolder(): Boolean {
    return coupon.product.id == 0L && !isLoading
}

fun ProductSearchUiState.showEmptyPlaceHolder(): Boolean {
    return products.isEmpty() && !isLoading
}
// endregion