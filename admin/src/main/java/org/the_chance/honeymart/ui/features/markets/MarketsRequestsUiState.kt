package org.the_chance.honeymart.ui.features.markets

import org.the_chance.honeymart.domain.model.MarketRequest
import org.the_chance.honeymart.domain.util.ErrorHandler

data class MarketsRequestUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: ErrorHandler? = null,
    val snackBar: SnackBarState = SnackBarState(),
    val requests: List<MarketRequestUiState> = emptyList(),
    val selectedRequest: MarketRequestUiState? = null,
    val requestsState: RequestsState = RequestsState.UNAPPROVED,
    val adminName: Char = 'n',
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
    val state: RequestsState = RequestsState.UNAPPROVED,
    val isSelected: Boolean = false
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
        isApproved = isApproved
    )
}

data class SnackBarState(
    val isShow: Boolean = false,
    val message: String = "",
)

enum class RequestsState { UNAPPROVED, APPROVED, ALL, }

fun MarketsRequestUiState.emptyRequestsPlaceHolder() =
    this.requests.isEmpty() && !this.isError && !this.isLoading

fun MarketsRequestUiState.contentScreen() =
    !this.isLoading && !this.isError

fun MarketsRequestUiState.navRailVisibility() = this.isLoggedIn
