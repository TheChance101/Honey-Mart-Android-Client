package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.MarketEntity


data class MarketsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val markets: List<MarketUiState> = emptyList(),
)

data class MarketUiState(
    val marketId: Long? = 0L,
    val marketName: String? = "",
)


fun MarketEntity.toMarketUiState(): MarketUiState {
    return MarketUiState(
        marketId = marketId,
        marketName = marketName
    )
}
