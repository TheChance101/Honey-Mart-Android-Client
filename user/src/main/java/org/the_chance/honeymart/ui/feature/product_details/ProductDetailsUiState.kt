package org.the_chance.honeymart.ui.feature.product_details

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.product.ProductUiState

data class ProductDetailsUiState(
    val isLoading: Boolean = true,
    val isAddToCartLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val totalPrice: Double = 0.0,
    val isConnectionError: Boolean = false,
    val product: ProductUiState = ProductUiState(),
    val image: String = "",
    val navigateToAuthGraph: NavigationState = NavigationState(),
    val smallImages: List<String> = emptyList(),
    val quantity: Int = 1,
)

data class NavigationState(
    val isNavigate: Boolean = false,
    val id: Long = 0L
)