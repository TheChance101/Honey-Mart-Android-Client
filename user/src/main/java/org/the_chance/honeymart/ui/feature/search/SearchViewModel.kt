package org.the_chance.honeymart.ui.feature.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.usecase.SearchForProductUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchForProductUseCase: SearchForProductUseCase
) : BaseViewModel<SearchUiState, SearchUiEffect>(SearchUiState()), SearchInteraction {
    override val TAG: String = this::class.simpleName.toString()
    private var page = _state.value.page
    private val actionStream = MutableSharedFlow<String>()
    private val sortOrder = _state.value.searchStates.state
    private var productListScrollPosition = 0

    init {
        observeKeyword()
    }

    private fun searchForProducts() {
        resetSearchState()
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            val products = searchForProductUseCase(_state.value.searchQuery, page, sortOrder)
            val mappedProducts = products!!.map { it.toSearchProductUiState() }
            _state.update { it.copy(products = mappedProducts, loading = false) }
        }
    }

    private fun appendProducts(products: List<SearchProductUiState>) {
        val current = ArrayList(state.value.products)
        current.addAll(products)
        _state.update { it.copy(products = current) }
    }

    private fun incrementPage() {
        page += 1
    }

    fun onChangeProductScrollPosition(position: Int) {
        productListScrollPosition = position
    }

    private fun resetSearchState() {
        _state.update { it.copy(products = listOf()) }
        page = 1
        onChangeProductScrollPosition(0)
    }

    private fun onSearchForProductsSuccess(products: List<Product>) {
        _state.update { searchUiState ->
            searchUiState.copy(
                // products = products.map { it.toProductUiState() },
            )
        }
    }

    private fun onSearchForProductsError(error: ErrorHandler) {
        _state.update { it.copy(error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    fun onSearchTextChange(text: String) {
        _state.update { it.copy(loading = true, searchQuery = text) }
        viewModelScope.launch { actionStream.emit(text) }
    }

    private fun observeKeyword() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            actionStream.debounce(700).distinctUntilChanged().filter { keyword ->
                keyword.isNotBlank()
            }.collect { searchForProducts() }
        }
    }


    override fun onClickFilter() {
        val newState = !state.value.filtering
        _state.update { it.copy(filtering = newState) }
    }

    override fun onClickRandomSearch() {
        filterSearch(SearchStates.RANDOM.state)
    }

    override fun onClickAscendingSearch() {
        filterSearch(SearchStates.ASCENDING.state)
    }

    override fun onClickDescendingSearch() {
        filterSearch(SearchStates.DESCENDING.state)
    }

    private fun filterSearch(sortOrder: String) {
        when (sortOrder) {
            SearchStates.ASCENDING.state -> _state.update { it.copy(searchStates = SearchStates.ASCENDING) }
            SearchStates.DESCENDING.state -> _state.update { it.copy(searchStates = SearchStates.DESCENDING) }
            else -> {
                _state.update { it.copy(searchStates = SearchStates.RANDOM) }
            }
        }
        if (_state.value.searchQuery.isNotBlank()) {
            searchForProducts()
        }
    }

    override fun onClickProduct(productId: Long) {
        effectActionExecutor(_effect, SearchUiEffect.OnClickProductCard(productId))
    }

    override fun onclickTryAgain() {
        searchForProducts()
    }

    override fun onScrollDown() {
        viewModelScope.launch {
            if ((productListScrollPosition + 1) >= (page * 10)) {
                _state.update { it.copy(loading = true) }
                incrementPage()
                if (page > 1) {
                    val result = searchForProductUseCase(
                        _state.value.searchQuery,
                        page,
                        sortOrder
                    )!!.map { it.toSearchProductUiState() }
                    appendProducts(result)
                }
                _state.update { it.copy(loading = false) }
            }
        }
    }
}