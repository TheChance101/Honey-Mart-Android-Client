package org.the_chance.honeymart.ui.features.requests

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.RequestEntity
import org.the_chance.honeymart.domain.usecase.GetRequestsUseCase
import org.the_chance.honeymart.domain.usecase.UpdateMarketRequestUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val getMarketRequests: GetRequestsUseCase,
    private val updateMarketRequest: UpdateMarketRequestUseCase,
) : BaseViewModel<RequestsUiState, RequestsUiEffect>(RequestsUiState()),
    RequestsInteractionListener {
    override val TAG: String = this::class.java.simpleName

    init {
        getRequests()
        Log.e("TAG", "Requests:Size is ${state.value.requests.size}")
    }

    private fun getRequests(isApproved: Boolean = false) {
        _state.update { it.copy(isLoading = true,isError = false) }
        tryToExecute(
            { getMarketRequests(isApproved) },
            ::onMarketRequestSuccess,
            ::onUpdateRequestError
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

    override fun updateRequest(marketId: Int, isApproved: Boolean) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { updateMarketRequest(marketId.toLong(), isApproved) },
            ::onUpdateRequestSuccess,
            ::onUpdateRequestError
        )
    }

    private fun onUpdateRequestSuccess(isApproved: Boolean) {
        _state.update { it.copy(isLoading = false) }
    }

    private fun onUpdateRequestError(error: ErrorHandler) {
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
        getRequests(isApproved)
    }

    override fun onClickRequest(position: Int) {
        val updatedRequests = _state.value.requests.mapIndexed { index, request ->
            request.copy(isSelected = index == position)
        }
        _state.update { it.copy(requests = updatedRequests, selectedRequest = updatedRequests[position]) }
        effectActionExecutor(_effect, RequestsUiEffect.onClickRequest)
    }


    override fun onClickCancel(marketId: Int) {
        _state.update { it.copy(selectedRequest = null) }
        updateRequest(marketId,false)
    }

    override fun onClickApprove(marketId: Int) {
        _state.update { it.copy(selectedRequest = null) }
        updateRequest(marketId,true)
    }

    override fun resetSnackBarState() {
        TODO("Not yet implemented")
    }
}