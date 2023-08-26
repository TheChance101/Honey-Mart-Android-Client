package org.the_chance.honeymart.ui.feature.search

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.the_chance.honeymart.domain.model.ProductEntity

import org.the_chance.honeymart.domain.util.ErrorHandler

data class SearchUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val updatedProducts: Flow<PagingData<ProductUiState>> = flow{},
    val products: Flow<PagingData<ProductUiState>> = flow{},
    val searchStates: SearchStates = SearchStates.RANDOM,
    val filtering: Boolean = false
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