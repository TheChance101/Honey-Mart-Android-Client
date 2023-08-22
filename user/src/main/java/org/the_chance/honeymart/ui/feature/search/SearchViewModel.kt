package org.the_chance.honeymart.ui.feature.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
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


    private fun searchForProducts(query: String) {
        tryToExecutePaging(
            { searchForProductUseCase(query) },
            ::searchForProductsSuccess,
            ::onError
        )
    }

    private fun searchForProductsSuccess(products: PagingData<ProductEntity>) {
        val mappedProducts = products.map { it.toProductUiState() }
        _state.update { searchUiState ->
            searchUiState.copy(
                products = flowOf(mappedProducts),
                updatedProducts = flowOf(mappedProducts)
            )
        }
        filter()
    }

    private suspend fun collectPagingDataToList(pagingData: Flow<PagingData<ProductUiState>>): List<ProductUiState> {
        val list = mutableListOf<ProductUiState>()
        pagingData.collect { paging ->
            paging.map { productUiState ->
                list.add(productUiState)
            }
        }
        return list
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        viewModelScope.launch { actionStream.emit(text) }
    }


    private fun observeKeyword() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            actionStreamDebounced.collect { searchForProducts(it) }
        }
    }


    override fun onClickFilter() {
        if (state.value.filtering) {
            _state.update { it.copy(filtering = false) }
        } else {
            _state.update { it.copy(filtering = true) }
        }
    }


    override fun onClickRandomSearch() {
        _state.update { it.copy(searchStates = SearchStates.RANDOM) }
        filter()
    }

    override fun onClickAscendingSearch() {
        _state.update { it.copy(searchStates = SearchStates.ASCENDING) }
        filter()
    }

    override fun onClickDescendingSearch() {
        _state.update { it.copy(searchStates = SearchStates.DESCENDING) }
        filter()
    }

    private fun filter() {
        viewModelScope.launch {
            val sortedProducts = when (_state.value.searchStates) {
                SearchStates.RANDOM -> collectPagingDataToList(_state.value.updatedProducts)
                SearchStates.ASCENDING -> collectPagingDataToList(_state.value.updatedProducts)
                    .sortedBy { it.productPrice }

                SearchStates.DESCENDING -> collectPagingDataToList(_state.value.updatedProducts)
                    .sortedByDescending { it.productPrice }
            }
            _state.update {
                it.copy(updatedProducts = flowOf(PagingData.from(sortedProducts)))
            }
        }
    }


    private fun onError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }


    override fun onClickProduct(productId: Long) {
        effectActionExecutor(_effect, SearchUiEffect.OnClickProductCard(productId))
    }

}

