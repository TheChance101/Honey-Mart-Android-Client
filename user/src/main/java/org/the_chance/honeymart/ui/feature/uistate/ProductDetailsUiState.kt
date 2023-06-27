package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.util.ErrorHandler

data class ProductDetailsUiState(
    val isLoading: Boolean = true,
    val isAddToCartLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val product: ProductUiState = ProductUiState(),
    val image: String = "",
    val smallImages: List<String> = emptyList(),
    val quantity: Int = 0,
)