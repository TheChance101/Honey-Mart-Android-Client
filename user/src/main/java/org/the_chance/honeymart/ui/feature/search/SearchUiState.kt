package org.the_chance.honeymart.ui.feature.search

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import org.the_chance.honeymart.domain.model.ProductEntity

import org.the_chance.honeymart.domain.util.ErrorHandler

data class SearchUiState(
    val isSearching: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val products: Flow<PagingData<ProductUiState>> = flow{},
    val searchStates: SearchStates = SearchStates.RANDOM,
    val filtering: Boolean = false,
    val searchText: MutableStateFlow<String> = MutableStateFlow(""),
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

fun ProductEntity.toProductUiState(): ProductUiState {
    return ProductUiState(
        productId = productId,
        productName = productName,
        productPrice = productPrice,
        productImages = productImages
    )
}