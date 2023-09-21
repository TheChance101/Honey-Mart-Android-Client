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
import org.the_chance.honeymart.ui.feature.SeeAllmarkets.MarketViewModel.Companion.MAX_PAGE_SIZE
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

    private fun searchForProducts() {
        _state.update { it.copy(isLoading = true, isError = false, error = null) }
        val value = state.value
        viewModelScope.launch {
            tryToExecute(
                {
                    searchForProductUseCase(
                        value.searchQuery,
                        value.page,
                        value.searchStates.state
                    )!!
                },
                ::searchSuccess,
                ::searchError
            )
        }
    }

    private fun searchSuccess(products: List<Product>) {
        _state.update {
            it.copy(
                isLoading = false,
                products = it.products.toMutableList().apply {
                    this.addAll(products.map { it.toSearchProductUiState() })
                }
            )
        }
    }

    private fun searchError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error, isError = true) }
    }

    override fun onChangeProductScrollPosition(position: Int) {
        if (position + 1 >= state.value.page * MAX_PAGE_SIZE) {
            _state.update { it.copy(page = it.page + 1) }
            searchForProducts()
        }
    }

    private fun resetSearch() {
        _state.update { it.copy(products = listOf()) }
        onChangeProductScrollPosition(0)
    }

    fun onSearchTextChange(text: String) {
        _state.update { it.copy(searchQuery = text) }
        viewModelScope.launch { actionStream.emit(text) }
    }

    private fun observeKeyword() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            actionStream.debounce(700).distinctUntilChanged().filter { keyword ->
                keyword.isNotBlank()
            }.collect {
                searchForProducts()
                resetSearch()
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
        if (state.value.searchQuery.isNotBlank()) {
            searchForProducts()
        }
    }

    override fun onClickProduct(productId: Long) {
        effectActionExecutor(_effect, SearchUiEffect.OnClickProductCard(productId))
    }

    override fun onclickTryAgain() {
        searchForProducts()
    }
}