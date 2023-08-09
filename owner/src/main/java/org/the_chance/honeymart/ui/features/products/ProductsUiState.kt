package org.the_chance.honeymart.ui.features.products

import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class ProductsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmptyProducts: Boolean = false,
    val error: ErrorHandler? = null,
    val products: List<ProductUiState> = emptyList(),
    val productsQuantity: String = "",
    val category: CategoryUiState = CategoryUiState(0, ""),
)

data class ProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productImage: String = "",
    val productPrice: String = "0.0",
)

data class CategoryUiState(
    val categoryIcon: Int = 0,
    val categoryName: String = ""
)

fun ProductEntity.toProductUiState(): ProductUiState {
    return ProductUiState(
        productId = productId,
        productName = productName,
        productPrice = "$ProductPrice$",
        productImage = productImages[0],
    )
}

fun ProductsUiState.contentScreen() = !this.isLoading && !this.isError
