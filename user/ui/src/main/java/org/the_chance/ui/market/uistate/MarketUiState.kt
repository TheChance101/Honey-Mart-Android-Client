package org.the_chance.ui.market.uistate


data class MarketUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val message: String = "",
    val markets: List<MarketsUiState> = emptyList(),
)

