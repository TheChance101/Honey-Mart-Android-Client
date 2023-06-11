package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.Market
import org.the_chance.honeymart.ui.base.ErrorState


data class MarketsUiState(
    val isLoading: Boolean = true,
    val error: ErrorState? = null,
    val markets: List<MarketUiState> = emptyList(),
)

data class MarketUiState(
    val marketId: Long? = 0L,
    val marketName: String? = "",
)


fun Market.asMarketUiState(): MarketUiState {
    return MarketUiState(
        marketId = marketId,
        marketName = marketName
    )
}
