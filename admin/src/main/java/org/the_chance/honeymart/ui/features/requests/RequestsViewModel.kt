package org.the_chance.honeymart.ui.features.requests

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.RequestEntity
import org.the_chance.honeymart.domain.usecase.GetAllMarketsUseCase
import org.the_chance.honeymart.domain.usecase.GetRequestsUseCase
import org.the_chance.honeymart.domain.usecase.UpdateMarketRequestUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val getMarketRequests: GetRequestsUseCase,
    private val updateRequest: UpdateMarketRequestUseCase,
//    private val getApprovedMarkets: GetAllMarketsUseCase,
) : BaseViewModel<RequestsUiState, RequestsUiEffect>(RequestsUiState()),
    RequestsInteractionListener {
    override val TAG: String = this::class.java.simpleName

    init {
        getAllRequests()
        Log.e("TAG", "Requests:Size is ${state.value.requests.size}")
    }

    private fun getAllRequests() {
        _state.update { it.copy(isLoading = true,isError = false) }
        tryToExecute(
            { getMarketRequests() },
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
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
        Log.e("TAG", "Requests:Error is ${state.value.error}")
    }

    override fun updateRequests(position: Long, isApproved: Boolean) {
        val marketId = state.value.requests[position.toInt()].marketId.toLong()
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { updateRequest(marketId, isApproved) },
            ::updateRequestsSuccess,
            ::onMarketRequestError
        )
    }

    private fun updateRequestsSuccess(isApproved: Boolean) {
        _state.update { it.copy(isLoading = false) }
    }

    override fun onGetAllRequests() {
        getAllRequests()
    }

    override fun onGetApproved() {
//        _state.update { it.copy(isLoading = true,isError = false) }
//        tryToExecute(
//            {getApprovedMarkets().map { it.to }},
//            ::onApprovedMarketsSuccess,
//            ::onApprovedMarketsError
//        )
    }

    private fun onApprovedMarketsSuccess(requests: List<RequestEntity>) {
        _state.update { requestUiState ->
            requestUiState.copy(
                isLoading = false,
                requests = requests.map { it.toRequestUiState() })
        }
        Log.e(TAG, "Requests:Value is ${state.value}")
    }

    private fun onApprovedMarketsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
        Log.e("TAG", "Requests:Error is ${state.value.error}")
    }
    override fun onClickRequest(position: Int) {
        val updatedRequests = _state.value.requests.mapIndexed { index, request ->
            request.copy(isSelected = index == position)
        }
        _state.update { it.copy(requests = updatedRequests, selectedRequest = updatedRequests[position]) }
        effectActionExecutor(_effect, RequestsUiEffect.onClickRequest)
    }


    override fun onClickCancel() {
        _state.update { it.copy(selectedRequest = null) }
    }

    override fun onClickApprove() {
        TODO("Not yet implemented")
    }

    override fun resetSnackBarState() {
        TODO("Not yet implemented")
    }
}