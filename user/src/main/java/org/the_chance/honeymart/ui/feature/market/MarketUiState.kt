package org.the_chance.honeymart.ui.feature.market

import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.util.ErrorHandler


data class MarketsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val markets: List<MarketUiState> = emptyList(),
)

data class MarketUiState(
    val marketId: Long = 0L,
    val marketName: String = "",
    val marketImage: String = "",
)


fun MarketEntity.toMarketUiState(): MarketUiState {
    return MarketUiState(
        marketId = marketId,
        marketName = marketName,
        marketImage = imageUrl
    )
}
