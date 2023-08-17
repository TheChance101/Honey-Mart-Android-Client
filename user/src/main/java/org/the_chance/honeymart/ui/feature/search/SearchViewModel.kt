package org.the_chance.honeymart.ui.feature.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() :
    BaseViewModel<SearchUiState, SearchUiEffect>(SearchUiState()), SearchInteraction {
    override val TAG: String = this::class.simpleName.toString()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    private val _products = MutableStateFlow(allProducts)

    val products = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_products) { text, products ->
            if (text.isBlank()) {
                products
            } else {
                delay(2000L)
                products.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }

        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _products.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
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

    data class Products(
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
    }
}


private val allProducts = listOf(
    SearchViewModel.Products(
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    ),
    SearchViewModel.Products(
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    ),
    SearchViewModel.Products(
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    ),
    SearchViewModel.Products(
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    ),
    SearchViewModel.Products(
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    ),
    SearchViewModel.Products(
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    ),
    SearchViewModel.Products(
        image = "",
        title = "game",
        price = "30,000",
        marketName = "asoak"
    )
)
