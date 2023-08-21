package org.the_chance.honeymart.ui.features.product_details

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.features.products.CategoryUiState
import org.the_chance.honeymart.ui.features.products.ProductsUiState

data class ProductsDetailsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmptyProducts: Boolean = false,
    val error: ErrorHandler? = null,
    val products: List<ProductDetailsUiState> = emptyList(),
    val productsQuantity: String = "",
    val category: CategoryUiState = CategoryUiState(0, ""),
    val id: Long = 0L,
    val name: String = "",
    val price: String = "",
    val description: String = "",
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

fun ProductsDetailsUiState.contentScreen() = !this.isLoading && !this.isError