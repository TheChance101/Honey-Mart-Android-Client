package org.the_chance.honeymart.ui.features.requests

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.RequestEntity
import org.the_chance.honeymart.domain.usecase.GetOwnerInfoUseCase
import org.the_chance.honeymart.domain.usecase.GetRequestsUseCase
import org.the_chance.honeymart.domain.usecase.UpdateRequestStateUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val getRequests: GetRequestsUseCase,
    private val updateRequestState: UpdateRequestStateUseCase,
    private val ownerProfileInfo: GetOwnerInfoUseCase,
) : BaseViewModel<RequestsUiState, RequestsUiEffect>(RequestsUiState()),
    RequestsInteractionListener {
    override val TAG: String = this::class.java.simpleName

    init {
        getAllRequests()
        Log.e("TAG", "Requests:Size is ${state.value.requests.size}")
        getOwnerInfo()
    }

    private fun getOwnerInfo() {
        _state.update {
            it.copy(
                ownerNameFirstCharacter = ownerProfileInfo.getOwnerNameFirstCharacter(),
                ownerImage = ownerProfileInfo.getOwnerImageUrl()
            )
        }
    }

    private fun getAllRequests() {
        tryToExecute(
            { getRequests(RequestsStates.ALL_REQUESTS.state) },
            ::onRequestSuccess,
            ::onError
        )
    }

    private fun onRequestSuccess(requests: List<RequestEntity>) {
        _state.update { requestUiState ->
            requestUiState.copy(
                requestsStates = RequestsStates.ALL_REQUESTS,
                requests = requests.map { it.toRequestUiState() })
        }
        Log.e("TAG", "Requests:Value is ${state.value}")
    }

    private fun onError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
        Log.e("TAG", "Requests:Error is ${state.value.error}")
    }

    override fun updateRequests(position: Long, orderState: Int) {
        val marketId = state.value.requests[position.toInt()].marketId.toLong()
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { updateRequestState(marketId, orderState) },
            ::updateRequestsSuccess,
            ::onError
        )
    }

    private fun updateRequestsSuccess(state: Boolean) {
        _state.update { it.copy(isLoading = false) }
        when (_state.value.requestsStates) {
            RequestsStates.ALL_REQUESTS -> onClickAllRequests()
            RequestsStates.NEW_REQUESTS -> onClickNewRequests()
            RequestsStates.APPROVED -> onClickApproved()
        }
    }

    override fun onClickAllRequests() {
        _state.update {
            it.copy(requestsStates = RequestsStates.ALL_REQUESTS)
        }
        getAllRequests()
    }

    override fun onClickNewRequests() {
        _state.update {
            it.copy(requestsStates = RequestsStates.NEW_REQUESTS)
        }
    }

    override fun onClickApproved() {
        _state.update {
            it.copy(
                requestsStates = RequestsStates.APPROVED,)
        }
    }

    override fun onClickRequest() {
        _state.update { it.copy(isRequestSelected = true) }
        effectActionExecutor(_effect, RequestsUiEffect.onClickRequest)
    }

    override fun onClickCancel() {
        TODO("Not yet implemented")
    }

    override fun onClickApprove() {
        TODO("Not yet implemented")
    }

    override fun resetSnackBarState() {
        TODO("Not yet implemented")
    }
}