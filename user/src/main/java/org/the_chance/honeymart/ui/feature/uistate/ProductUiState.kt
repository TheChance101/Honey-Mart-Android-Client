package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.Product

data class ProductsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val products: List<ProductUiState> = emptyList(),
    val categories: List<CategoryUiState> = emptyList(),
)

data class ProductUiState(
    val productId: Long? = 0L,
    val productName: String? = "",
    val productQuantity: String? = "",
    val productPrice: Double? = 0.0,
    val isFavorite: Boolean? = false
)

fun Product.asProductUiState(): ProductUiState {
    return ProductUiState(
        productId = productId,
        productName = productName,
        productQuantity = productQuantity,
        productPrice = ProductPrice,
    )
}