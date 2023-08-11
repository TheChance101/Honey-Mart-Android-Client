package org.the_chance.honeymart.ui.feature.wishlist

import android.icu.text.DecimalFormat
import org.the_chance.honeymart.domain.model.WishListEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class WishListUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val snackBar :SnackBarState = SnackBarState(),
    val error: ErrorHandler? = null,
    val products: List<WishListProductUiState> = emptyList(),
)
data class SnackBarState(
    val isShow : Boolean = false ,
    val productId: Long =0L,
    val message: String = "",
)

data class WishListProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productPrice: Double = 0.0,
    val isFavorite: Boolean = true,
    val description: String = "",
    val productImages: List<String> = emptyList()
)

fun WishListEntity.toWishListProductUiState(): WishListProductUiState {
    return WishListProductUiState(
        productId = productId,
        productName = name,
        productPrice = price,
        productImages = images,
        description = description
    )
}




fun formatCurrencyWithNearestFraction(amount: Double):String {
    val decimalFormat = DecimalFormat("#,##0.0'$'")
    return decimalFormat.format(amount)
}

fun WishListUiState.firstLoading() = this.isLoading && this.products.isEmpty()

fun WishListUiState.emptyPlaceholder() = this.products.isEmpty() && !this.isError && !this.isLoading

fun WishListUiState.contentScreen() = this.products.isNotEmpty() && !this.isError

fun WishListUiState.loading() = this.isLoading && this.products.isNotEmpty()
