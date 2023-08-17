package org.the_chance.honeymart.ui.feature.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.usecase.SearchForProductUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchForProductUseCase: SearchForProductUseCase
) : BaseViewModel<SearchUiState, SearchUiEffect>(SearchUiState()), SearchInteraction {
    override val TAG: String = this::class.simpleName.toString()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private fun searchForProducts(query: String) {
        tryToExecute(
            { searchForProductUseCase(query) },
            ::searchForProductsSuccess,
            ::searchForProductsError
        )
    }

    private fun searchForProductsSuccess(products: List<ProductEntity>) {
        _state.update { searchUiState ->
            searchUiState.copy(products = products.map { it.toProductUiState() })
        }
    }

    private fun searchForProductsError(error: ErrorHandler) {

    }

    val products = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_state) { text, SearchUiState ->
            if (text.isBlank()) {
                searchForProducts("")
            } else {
                searchForProducts(text)
            }
        }
        .onEach { _isSearching.update { false } }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    override fun onClickFilter() {
        if (state.value.filtering) {
            _state.update { it.copy(filtering = false) }
        } else {
            _state.update { it.copy(filtering = true) }
        }
    }


    override fun getAllRandomSearch() {
        TODO("Not yet implemented")
    }

    override fun getAllAscendingSearch() {
        TODO("Not yet implemented")
    }

    override fun getAllDescendingSearch() {
        TODO("Not yet implemented")
    }

    override fun onClickProduct(productId: Long) {
        effectActionExecutor(
            _effect,
            SearchUiEffect.OnClickProductCard(productId)
        )
    }

    /*data class Products(
        val productId: Long,
        val image: String,
        val title: String,
        val price: String,
        val marketName: String
    ) {
        fun doesMatchSearchQuery(query: String): Boolean {
            val matchingCombinations = listOf(
                "$title $price $marketName",
                "$title $price $marketName",
                "${title.first()} ${marketName.first()}",
            )

            return matchingCombinations.any {
                it.contains(query, ignoreCase = true)
            }
        }
    }*/
}


/*private val allProducts = listOf(
    SearchViewModel.Products(
        productId = 23,
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    ),
    SearchViewModel.Products(
        productId = 23,
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    ),
    SearchViewModel.Products(
        productId = 23,
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    ),
    SearchViewModel.Products(
        productId = 23,
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    ),
    SearchViewModel.Products(
        productId = 23,
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    ),
    SearchViewModel.Products(
        productId = 23,
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    ),
    SearchViewModel.Products(
        productId = 23,
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    )
)*/
