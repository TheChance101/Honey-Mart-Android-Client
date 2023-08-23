package org.the_chance.honeymart.ui.features.requests

import org.the_chance.honeymart.domain.model.RequestEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class RequestsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val snackBar: SnackBarState = SnackBarState(),
    val requests: List<RequestUiState> = emptyList(),
    val selectedRequest: RequestUiState? = null,
    val requestsStates: RequestsStates = RequestsStates.UNAPPROVED
)

data class RequestUiState(
    val marketId: Int = 0,
    val ownerName: String = "",
    val marketName: String = "",
    val marketAddress: String = "",
    val marketDescription: String = "",
    val ownerEmail: String = "",
    val isRequestNew: Boolean = false,
    val isRequestSelected: Boolean = false,
    val state: RequestsStates = RequestsStates.UNAPPROVED,
    val isSelected: Boolean = false
) {
    fun ownerNameFirstCharacter(): Char = this.ownerName.first()
}

fun RequestEntity.toRequestUiState(): RequestUiState {
    return RequestUiState(
        marketId = marketId,
        marketName = marketName,
        ownerName = ownerName,
        ownerEmail = ownerEmail,
        marketAddress = marketAddress,
        marketDescription = marketDescription
    )
}

data class SnackBarState(
    val isShow: Boolean = false,
    val message: String = "",
)

enum class RequestsStates(val state: Int) {
    UNAPPROVED(1),
    APPROVED(2)
}

fun RequestsUiState.emptyRequestsPlaceHolder() =
    this.requests.isEmpty() && !this.isError && !this.isLoading

fun RequestsUiState.contentScreen() = !this.isLoading && !this.isError
