package org.the_chance.honeymart.ui.features.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.MarketInfo
import org.the_chance.honeymart.domain.model.OwnerProfile
import org.the_chance.honeymart.domain.usecase.OwnerProfileManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val ownerProfile: OwnerProfileManagerUseCase
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
            function = { ownerProfile.getOwnerProfileUseCase() },
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
            function = { ownerProfile.getMarketInfoUseCase() },
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
        dismessStatusDialog()
        val newStatus = if (status == MarketStatus.ONLINE.state)
            MarketStatus.OFFLINE.state else MarketStatus.ONLINE.state
        tryToExecute(
            function = { ownerProfile.updateMarketStatusUseCase(newStatus) },
            onSuccess = ::onUpdateMarketInfoSuccess,
            onError = ::onUpdateMarketInfoError
        )
    }

    override fun dismessStatusDialog() {
        _state.update { it.copy(showMarketStatusDialog = false) }
    }

    override fun showStatusDialog() {
        _state.update { it.copy(showMarketStatusDialog = true) }
    }

    private fun onUpdateMarketInfoSuccess(status: Boolean) {
        toggleMarketStatus()
        _state.update { it.copy(error = null, isError = false) }
    }

    private fun onUpdateMarketInfoError(errorHandler: ErrorHandler) {
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    private fun toggleMarketStatus() {
        _state.update {
            val newStatus =
                if (it.marketInfo.status == MarketStatus.ONLINE) MarketStatus.OFFLINE else MarketStatus.ONLINE
            it.copy(marketInfo = it.marketInfo.copy(status = newStatus))
        }
    }
    // endregion
}