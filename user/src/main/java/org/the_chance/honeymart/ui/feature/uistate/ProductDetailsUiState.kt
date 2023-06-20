package org.the_chance.honeymart.ui.feature.uistate

data class ProductDetailsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val product: ProductUiState = ProductUiState(),
    val image: String = " ",
    val smallImages: List<String> = emptyList()
)