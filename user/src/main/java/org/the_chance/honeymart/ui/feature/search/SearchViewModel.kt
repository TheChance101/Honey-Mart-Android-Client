package org.the_chance.honeymart.ui.feature.search

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
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
            ::onError
        )
    }

    private fun searchForProductsSuccess(products: List<ProductEntity>) {
        _state.update { searchUiState ->
            searchUiState.copy(products = products.map { it.toProductUiState() })
        }
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
        _state.update {
            it.copy(isLoading = true, searchStates = SearchStates.RANDOM, isError = false)
        }
        tryToExecute(
            { searchForProductUseCase(SearchStates.RANDOM.toString()).map { it.toProductUiState() } },
            ::onGetRandomSuccess,
            ::onError
        )
    }

    private fun onGetRandomSuccess(products: List<ProductUiState>) {
        _state.update { it.copy(isLoading = false, products = products) }
    }


    override fun getAllAscendingSearch() {
        _state.update {
            it.copy(isLoading = true, searchStates = SearchStates.ASCENDING, isError = false)
        }
        tryToExecute(
            { searchForProductUseCase(SearchStates.ASCENDING.toString()).map { it.toProductUiState() } },
            ::onGetAscendingSearchSuccess,
            ::onError
        )
    }

    private fun onGetAscendingSearchSuccess(products: List<ProductUiState>) {
        _state.update { it.copy(isLoading = false, products = products) }
        state.value.products.sortedBy {
            it.productPrice
        }
    }


    override fun getAllDescendingSearch() {
        _state.update {
            it.copy(isLoading = true, searchStates = SearchStates.DESCENDING, isError = false)
        }
        tryToExecute(
            { searchForProductUseCase(SearchStates.DESCENDING.toString()).map { it.toProductUiState() } },
            ::onGetDescendingSearchSuccess,
            ::onError
        )
    }

    private fun onGetDescendingSearchSuccess(products: List<ProductUiState>) {
        _state.update { it.copy(isLoading = false, products = products) }
        state.value.products.sortedByDescending {
            it.productPrice
        }
    }

    private fun onError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun onClickProduct(productId: Long) {
        effectActionExecutor(
            _effect,
            SearchUiEffect.OnClickProductCard(productId)
        )
    }
}

