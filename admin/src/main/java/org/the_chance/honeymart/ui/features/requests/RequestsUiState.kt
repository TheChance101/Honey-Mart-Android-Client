package org.the_chance.honeymart.ui.features.requests

import org.the_chance.honeymart.domain.model.RequestEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class RequestsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val snackBar: SnackBarState = SnackBarState(),
    val requestsStates: RequestsStates = RequestsStates.ALL_REQUESTS,
    val requests: List<RequestUiState> = emptyList(),
    val isRequestNew: Boolean = false,
    val isRequestSelected: Boolean = false,
    val userName: String = "",
    val userEmail: String = "",
    val userImage: String = "",
    val marketImage: Int  = 0,
    val marketName: String = "",
    val marketAddress: String = "",
    val marketDescription: String = "",
    )

data class RequestUiState(
    val marketId: Int = 0,
    val userName: String = "",
    val marketName: String = "",
    val userImage: String = "",
    val date: String = "",
)

fun RequestEntity.toRequestUiState(): RequestUiState {
    return RequestUiState(
        marketId = marketId,
        marketName = marketName,
    )
}

data class SnackBarState(
    val isShow: Boolean = false,
    val message: String = "",
)

enum class RequestsStates(val state: Int) {
    ALL_REQUESTS(1),
    NEW_REQUESTS(2),
    APPROVED(3)
}

fun RequestsUiState.allRequests() = this.requestsStates == RequestsStates.ALL_REQUESTS
fun RequestsUiState.newRequests() = this.requestsStates == RequestsStates.NEW_REQUESTS
fun RequestsUiState.approved() = this.requestsStates == RequestsStates.APPROVED

fun RequestsUiState.emptyRequestsPlaceHolder() = this.requests.isEmpty() && !this.isError && !this.isLoading

