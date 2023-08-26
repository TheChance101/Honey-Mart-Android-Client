package org.the_chance.honeymart.ui.features.requests

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
    }

    private fun onMarketRequestError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        when (error) {
            is ErrorHandler.NoConnection -> {
                _state.update { it.copy(isError = true) }
            }
            ErrorHandler.UnAuthorizedUser -> {
                _state.update { it.copy(isAuthenticationError = true) }
                effectActionExecutor(_effect, MarketsUiEffect.UnAuthorizedUserEffect)
            }
            else -> {}
        }
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
        _state.update { it.copy(selectedRequest = null) }
        updateMarket(marketId,false)
        getMarkets(_state.value.requestsStates == RequestsStates.UNAPPROVED)
    }

    override fun onClickApprove(marketId: Int) {
        _state.update { it.copy(selectedRequest = null) }
        updateMarket(marketId,true)
        getMarkets(_state.value.requestsStates == RequestsStates.APPROVED)
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