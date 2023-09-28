package org.the_chance.honeymart.ui.feature.cart

import android.icu.text.DecimalFormat
import org.the_chance.honeymart.domain.model.Cart
import org.the_chance.honeymart.domain.util.ErrorHandler

data class CartUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val total: Double = 0.0,
    val products: List<CartListProductUiState> = emptyList(),
    val bottomSheetIsDisplayed: Boolean = false,
    val showDialog :Boolean = false
)

data class CartListProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productPrice: Double = 0.0,
    val totalPrice: Double = 0.0,
    val productCount: Int = 0,
    val productImage: List<String> = emptyList()
) {
    val productImageUrl = productImage.takeIf { it.isNotEmpty() }?.firstOrNull() ?: ""
    val productPriceFormatted = formatCurrencyWithNearestFraction(totalPrice)
}

fun Cart.toCartListProductUiState(): CartUiState {
    return CartUiState(
        total = total,
        products = products.toCartProductUiState()
    )
}

fun List<Cart.Product>.toCartProductUiState(): List<CartListProductUiState> {
    return this.map {
        CartListProductUiState(
            productId = it.id,
            productName = it.name,
            productPrice = it.price,
            productCount = it.count,
            productImage = it.images,
            totalPrice = it.price * it.count
        )
    }
}


fun formatCurrencyWithNearestFraction(amount: Double): String {
    val decimalFormat = DecimalFormat("#,##0.0'$'")
    return decimalFormat.format(amount)
}


fun CartUiState.unpopulatedLoading() = this.isLoading && this.products.isEmpty()

fun CartUiState.errorPlaceHolderCondition() = this.isError

fun CartUiState.placeHolderCondition() = this.products.isEmpty() && !this.isError && !this.isLoading

fun CartUiState.cartSuccessScreenCondition() = this.products.isNotEmpty() && !this.isError

fun CartUiState.populatedLoading() = this.isLoading && this.products.isNotEmpty()
