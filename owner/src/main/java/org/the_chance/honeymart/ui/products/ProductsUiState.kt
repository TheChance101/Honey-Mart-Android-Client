package org.the_chance.honeymart.ui.products

import org.the_chance.honeymart.domain.util.ErrorHandler

data class ProductsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmptyProducts: Boolean = false,
    val error: ErrorHandler? = null,
    val products: List<ProductUiState> = emptyList(),
    val category: CategoryUiState = CategoryUiState(0, ""),
)

data class ProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productImage: String = "",
    val productPrice: Double = 0.0,
    val productQuantity: Int = 0
)

data class CategoryUiState(
    val categoryIcon: Int = 0,
    val categoryName: String = ""
)
