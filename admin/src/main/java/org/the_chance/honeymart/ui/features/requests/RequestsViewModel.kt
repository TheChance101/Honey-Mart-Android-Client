package org.the_chance.honeymart.ui.features.requests

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetRequestsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
private val getRequests: GetRequestsUseCase
):BaseViewModel<RequestsUiState, RequestsUiEffect>(RequestsUiState()), RequestsInteractionListener{
    override val TAG: String = this::class.java.simpleName

    private fun getAllRequests(){
        _state.update {
            it.copy(isLoading = true, isError = false)
        }
        tryToExecute(
            { getRequests(RequestsStates.ALL_REQUESTS.state).map { it.toRequestUiState()} },
            ::onSuccess,
            ::onError
        )
    }

    override fun onClickAllRequests() {
        _state.update {
            it.copy(requestsStates = RequestsStates.ALL_REQUESTS)
        }
        getAllRequests()
    }

    private fun onSuccess(requests: List<RequestUiState>) {
        _state.update { it.copy(isLoading = false, requests = requests) }
    }

    private fun onError(error: ErrorHandler){
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun onClickNewRequests() {
        _state.update {
            it.copy(requestsStates = RequestsStates.NEW_REQUESTS)
        }
    }

    override fun onClickApproved() {
        _state.update {
            it.copy(requestsStates = RequestsStates.APPROVED)
        }
    }

    override fun onClickRequest() {
        _state.update { it.copy(isRequestSelected = true) }
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