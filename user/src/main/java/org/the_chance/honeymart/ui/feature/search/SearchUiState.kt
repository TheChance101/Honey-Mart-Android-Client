package org.the_chance.honeymart.ui.feature.search

data class SearchUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val products: List<ProductUiState> = emptyList(),
    )

data class ProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productPrice: Double = 0.0,
    val marketName: String = "",
    val productImages: List<String> = emptyList()
)

fun SearchUiState.emptySearchPlaceHolder() = this.products.isEmpty() && !this.isError && !this.isLoading

fun SearchUiState.screenContent() = this.products.isNotEmpty() && !this.isError
