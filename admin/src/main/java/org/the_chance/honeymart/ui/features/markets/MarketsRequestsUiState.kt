package org.the_chance.honeymart.ui.features.markets

import org.the_chance.honeymart.domain.model.MarketRequest
import org.the_chance.honeymart.domain.util.ErrorHandler

data class MarketsRequestUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val markets: List<MarketRequestUiState> = emptyList(),
    val selectedMarket: MarketRequestUiState? = null,
    val marketsState: MarketsState = MarketsState.ALL,
    val adminName: Char = ' ',
    val adminImageUrl: String = "",
)

data class MarketRequestUiState(
    val marketId: Int = 0,
    val ownerName: String = "",
    val marketName: String = "",
    val marketAddress: String = "",
    val marketDescription: String = "",
    val isApproved: Boolean,
    val ownerEmail: String = "",
    val state: MarketsState = MarketsState.UNAPPROVED,
    val isSelected: Boolean = false,
    val imageUrl: String = ""
) {
    val marketStateText = if (this.isApproved) "Approved" else "Pending"
    fun ownerNameFirstCharacter(): Char = this.ownerName.firstOrNull() ?: ' '
}

fun MarketRequest.toMarketRequestUiState(): MarketRequestUiState {
    return MarketRequestUiState(
        marketId = marketId,
        marketName = marketName,
        ownerName = ownerName,
        ownerEmail = ownerEmail,
        marketAddress = marketAddress,
        marketDescription = marketDescription,
        isApproved = isApproved,
        imageUrl = imageUrl
    )
}
enum class MarketsState(val state: Int) {
    ALL(1),
    APPROVED(2),
    UNAPPROVED(3),
}

fun MarketsRequestUiState.emptyRequestsPlaceHolder() =
    this.markets.isEmpty() && !this.isError && !this.isLoading

fun MarketsRequestUiState.isContentScreenVisible() =  !this.isError