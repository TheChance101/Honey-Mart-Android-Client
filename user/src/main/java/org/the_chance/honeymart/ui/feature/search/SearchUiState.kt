package org.the_chance.honeymart.ui.feature.search

import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.util.ErrorHandler

data class SearchUiState(
    val isSearching: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val loading: Boolean = true,
    val page: Int = 1,
    val products: List<ProductUiState> = ArrayList(),
    val searchStates: SearchStates = SearchStates.RANDOM,
    val filtering: Boolean = false,
    val searchQuery: String = "",
)

data class ProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productPrice: Double = 0.0,
    val marketName: String = "",
    val productImages: List<String> = emptyList()
)

enum class SearchStates(val state: String) {
    RANDOM("random"),
    ASCENDING("asc"),
    DESCENDING("desc"),
}

fun SearchUiState.random() = this.searchStates == SearchStates.RANDOM
fun SearchUiState.ascending() = this.searchStates == SearchStates.ASCENDING
fun SearchUiState.descending() = this.searchStates == SearchStates.DESCENDING

fun Product.toProductUiState(): ProductUiState {
    return ProductUiState(
        productId = productId,
        productName = productName,
        productPrice = productPrice,
        productImages = productImages
    )
}