package org.the_chance.honeymart.ui.features.markets

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.RequestEntity
import org.the_chance.honeymart.domain.usecase.GetMarketRequests
import org.the_chance.honeymart.domain.usecase.LogOutAdminUseCase
import org.the_chance.honeymart.domain.usecase.UpdateMarketRequestUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MarketsViewModel @Inject constructor(
    private val getMarketRequests: GetMarketRequests,
    private val updateMarketRequest: UpdateMarketRequestUseCase,
    private val logOutAdmin: LogOutAdminUseCase
) : BaseViewModel<RequestsUiState, MarketsUiEffect>(RequestsUiState()),
    MarketsInteractionListener {
    override val TAG: String = this::class.java.simpleName

    init {
        getMarkets()
        Log.e("TAG", "Requests:Size is ${state.value.requests.size}")
    }

    private fun getMarkets(isApproved: Boolean = false) {
        _state.update { it.copy(isLoading = true,isError = false) }
        tryToExecute(
            { getMarketRequests(isApproved) },
            ::onMarketRequestSuccess,
            ::onMarketRequestError
        )
    }

    private fun onMarketRequestSuccess(requests: List<RequestEntity>) {
        _state.update { requestUiState ->
            requestUiState.copy(
                isLoading = false,
                requests = requests.map { it.toRequestUiState() })
        }
        Log.e(TAG, "Requests:Value is ${state.value}")
    }

    private fun onMarketRequestError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error, isError = true) }
        when (error) {
            is ErrorHandler.NoConnection -> {
                _state.update { it.copy(isError = true) }
            }
            ErrorHandler.UnAuthorizedUser -> {
                _state.update { it.copy(isAuthenticationError = true) }
                effectActionExecutor(_effect, MarketsUiEffect.UnAuthorizedUserEffect)
            }
            else -> {}
        Log.e(TAG, "Requests:Value is ${state.value}")
    }

    override fun updateMarket(marketId: Int, isApproved: Boolean) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { updateMarketRequest(marketId.toLong(), isApproved) },
            ::onUpdateMarketSuccess,
            ::onUpdateMarketError
        )
    }

    private fun onUpdateMarketSuccess(isApproved: Boolean) {
        _state.update { it.copy(isLoading = false) }
    }

    private fun onUpdateMarketError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
        Log.e("TAG", "Requests:Error is ${state.value.error}")
    }

    override fun onGetFilteredRequests(isApproved: Boolean) {
        _state.update { it.copy(
            requestsStates = if(isApproved) RequestsStates.APPROVED else RequestsStates.UNAPPROVED,
            selectedRequest = null)
        }
        getMarkets(isApproved)
    }

    override fun onClickRequest(position: Int) {
        val updatedRequests = _state.value.requests.mapIndexed { index, request ->
            request.copy(isSelected = index == position)
        }
        _state.update { it.copy(requests = updatedRequests, selectedRequest = updatedRequests[position]) }
        effectActionExecutor(_effect, MarketsUiEffect.onClickMarket)
    }

    override fun onClickCancel(marketId: Int) {
        val approvedMarket = _state.value.requests.find { it.marketId == marketId }
        val updatedMarkets = _state.value.requests.toMutableList().apply { remove(approvedMarket) }
        updateMarket(marketId,false)
        _state.update { it.copy(selectedRequest = null, requests = updatedMarkets) }
    }

    override fun onClickApprove(marketId: Int) {
        val approvedMarket = _state.value.requests.find { it.marketId == marketId }
        val updatedMarkets = _state.value.requests.toMutableList().apply { remove(approvedMarket) }
        updateMarket(marketId,true)
        _state.update { it.copy(selectedRequest = null, requests = updatedMarkets ) }
    }

    override fun onClickLogout() {
        tryToExecute(
            function = { logOutAdmin },
            onSuccess = { onLogoutSuccess() },
            onError = ::onLogoutError
        )
    }

    private fun onLogoutSuccess() {
        effectActionExecutor(_effect, MarketsUiEffect.OnClickLogoutEffect)
    }

    private fun onLogoutError(error: ErrorHandler) {

    }
}