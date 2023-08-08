package org.the_chance.honeymart.ui.feature.product_details

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.product.ProductUiState

data class ProductDetailsUiState(
    val isLoading: Boolean = false,
    val isAddToCartLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val totalPrice: Double = 0.0,
    val snackBar : SnackBarState = SnackBarState(),
    val isConnectionError: Boolean = false,
    val product: ProductUiState = ProductUiState(),
    val image: String = "",
    val smallImages: List<String> = emptyList(),
    val quantity: Int = 1,
)
data class SnackBarState(
    val isShow : Boolean = false ,
    val productId : Long = 0L ,
)



fun ProductDetailsUiState.contentScreen()= !this.isLoading && !this.isConnectionError