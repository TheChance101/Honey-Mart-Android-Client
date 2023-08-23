package org.the_chance.honeymart.ui.feature.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.model.ProductEntity
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

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val actionStream = MutableSharedFlow<String>()
    private val actionStreamDebounced = actionStream.debounce(1000)

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()


    init {
        observeKeyword()
    }

    private fun searchForProducts() {
        val query = _searchText.value
        val sortOrder = _state.value.searchStates.state
        tryToExecutePaging(
            { searchForProductUseCase(query, sortOrder) },
            ::onSearchForProductsSuccess,
            ::onSearchForProductsError
        )
    }

    private fun onSearchForProductsSuccess(products: PagingData<ProductEntity>) {
        _state.update { it.copy(isLoading = false, error = null, isError = false) }
        val mappedProducts = products.map { it.toProductUiState() }
        _state.update { searchUiState ->
            searchUiState.copy(
                products = flowOf(mappedProducts),
            )
        }
    }

    private fun onSearchForProductsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    fun onSearchTextChange(text: String) {
        _state.update { it.copy(isLoading = true, error = null, isError = false) }
        _searchText.value = text
        viewModelScope.launch { actionStream.emit(text) }
    }


    private fun observeKeyword() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            actionStreamDebounced.collect {
                searchForProducts()
            }
        }
    }


    override fun onClickFilter() {
        val newState = !state.value.filtering
        _state.update { it.copy(filtering = newState) }
    }


    override fun onClickRandomSearch() {
        filter(SearchStates.RANDOM.state)
    }

    override fun onClickAscendingSearch() {
        filter(SearchStates.ASCENDING.state)
    }

    override fun onClickDescendingSearch() {
        filter(SearchStates.DESCENDING.state)
    }

    private fun filter(sortOrder: String) {
        _state.update { it.copy(isLoading = true, error = null, isError = false) }
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

