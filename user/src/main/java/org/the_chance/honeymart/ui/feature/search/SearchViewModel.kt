package org.the_chance.honeymart.ui.feature.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
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

    private val actionStream = MutableSharedFlow<String>()

    init {
        observeKeyword()
    }

    fun searchForProducts() {
        _state.update { it.copy(isSearching = true, isError = false) }
        val query = _state.value.searchQuery
        val sortOrder = _state.value.searchStates.state
        tryToExecutePaging(
            { searchForProductUseCase(query, sortOrder) },
            ::onSearchForProductsSuccess,
            ::onSearchForProductsError
        )
    }

    private fun onSearchForProductsSuccess(products: PagingData<Product>) {
        _state.update { searchUiState ->
            searchUiState.copy(
                products = flowOf(products.map { it.toProductUiState() }),
            )
        }
    }

    private fun onSearchForProductsError(error: ErrorHandler) {
        _state.update { it.copy(error = error, ) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    fun onSearchTextChange(text: String) {
        _state.update { it.copy(isSearching = true, searchQuery = text) }
        viewModelScope.launch { actionStream.emit(text) }
    }

    private fun observeKeyword() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            actionStream.debounce(700).distinctUntilChanged().filter {
                it.isNotEmpty()
            }.collect {
                searchForProducts()
            }
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
        searchForProducts()
    }

    override fun onClickProduct(productId: Long) {
        effectActionExecutor(_effect, SearchUiEffect.OnClickProductCard(productId))
    }

    override fun onclickTryAgain() {
        searchForProducts()
    }
}

