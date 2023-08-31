package org.the_chance.honeymart.ui.features.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.MarketInfo
import org.the_chance.honeymart.domain.model.OwnerProfile
import org.the_chance.honeymart.domain.usecase.GetMarketInfoUseCase
import org.the_chance.honeymart.domain.usecase.GetOwnerProfileUseCase
import org.the_chance.honeymart.domain.usecase.UpdateMarketStatusUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMarketInfoUseCase: GetMarketInfoUseCase,
    private val getOwnerProfileUseCase: GetOwnerProfileUseCase,
    private val updateMarketStatusUseCase: UpdateMarketStatusUseCase
) :
    BaseViewModel<ProfileUiState, Unit>(ProfileUiState()), ProfileInteractionListener {
    override val TAG: String = this::class.java.simpleName

    init {
        getPersonalInfo()
        getMarketInfo()
    }

    // region Personal Info
    override fun getPersonalInfo() {
        _state.update { it.copy(isLoading = true, isError = false, error = null) }
        tryToExecute(
            function = { getOwnerProfileUseCase() },
            onSuccess = ::onGetPersonalInfoSuccess,
            onError = ::onGetPersonalInfoError
        )
    }

    private fun onGetPersonalInfoSuccess(ownerProfile: OwnerProfile) {
        _state.update {
            it.copy(
                isLoading = false,
                error = null,
                isError = false,
                personalInfo = ownerProfile.toPersonalInfoUiState()
            )
        }
    }

    private fun onGetPersonalInfoError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = errorHandler) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    // endregion


    // region Market Info
    override fun getMarketInfo() {
        _state.update { it.copy(isLoading = true, isError = false, error = null) }
        tryToExecute(
            function = { getMarketInfoUseCase() },
            onSuccess = ::onGetMarketInfoSuccess,
            onError = ::onGetMarketInfoError
        )
    }

    private fun onGetMarketInfoSuccess(marketInfo: MarketInfo) {
        _state.update {
            it.copy(
                isLoading = false,
                error = null,
                isError = false,
                marketInfo = marketInfo.toMarketInfoUiState()
            )
        }
    }

    private fun onGetMarketInfoError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = errorHandler) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun updateMarketStatus(status: Int) {
        _state.update { it.copy(showMarketStatusDialog = false) }
        val newStatus = if (status == MarketStatus.ONLINE.state)
            MarketStatus.OFFLINE.state else MarketStatus.ONLINE.state
        //        toggleMarketStatus()
        tryToExecute(
            function = { updateMarketStatusUseCase(newStatus) },
            onSuccess = ::onUpdateMarketInfoSuccess,
            onError = ::onUpdateMarketInfoError
        )
    }

    private fun onUpdateMarketInfoSuccess(status: Boolean) {
//        toggleMarketStatus()
        _state.update { it.copy(error = null, isError = false,) }
    }

    private fun onUpdateMarketInfoError(errorHandler: ErrorHandler) {
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    private fun toggleMarketStatus() {
        _state.update {
            val newStatus = if (it.marketInfo.status == MarketStatus.ONLINE) MarketStatus.OFFLINE else MarketStatus.ONLINE
            it.copy(marketInfo = it.marketInfo.copy(status = newStatus))
        }
    }
    // endregion
}