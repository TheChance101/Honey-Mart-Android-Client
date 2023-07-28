package org.the_chance.honeymart.ui.feature.product_details

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.product.ProductUiState

data class ProductDetailsUiState(
    val isLoading: Boolean = true,
    val isAddToCartLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isConnectionError: Boolean = false,
    val product: ProductUiState = ProductUiState(),
    val image: String = "",
    val smallImages: List<String> = emptyList(),
    val quantity: Int = 1,
)