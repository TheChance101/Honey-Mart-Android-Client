package org.the_chance.honeymart.ui.features.product_details

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState

data class ProductsDetailsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmptyProducts: Boolean = false,
    val error: ErrorHandler? = null,
    val products: List<ProductDetailsUiState> = emptyList(),
    val productsQuantity: String = "",
    val images: List<ByteArray> = emptyList(),
    val productNameState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val productPriceState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val productDescriptionState: ValidationState = ValidationState.VALID_TEXT_FIELD,
)

data class ProductDetailsUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productImage: String = "",
    val productPrice: String = "0.0",
)
