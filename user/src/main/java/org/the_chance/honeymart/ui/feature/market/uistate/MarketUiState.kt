package org.the_chance.ui.market.uistate

import org.the_chance.domain.model.Market


data class MarketsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val markets: List<MarketUiState> = emptyList(),
)

data class MarketUiState(
    val name: String = "",
    val id: Int = 0,
)

fun Market.asMarketsUiState(): MarketUiState {
    return MarketUiState(
        name = name
    )
}
