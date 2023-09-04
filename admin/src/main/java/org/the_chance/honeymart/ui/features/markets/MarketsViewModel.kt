package org.the_chance.honeymart.ui.features.markets

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.MarketRequest
import org.the_chance.honeymart.domain.usecase.usecaseManager.admin.AdminMarketsManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MarketsViewModel @Inject constructor(
    private val adminMarketsManager: AdminMarketsManagerUseCase
) : BaseViewModel<MarketsRequestUiState, MarketsUiEffect>(MarketsRequestUiState()),
    MarketsInteractionListener {
    override val TAG: String = this::class.java.simpleName

    init {
        getMarkets()
    }

    private fun getMarkets() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { adminMarketsManager.getMarkets() },
            ::onMarketRequestSuccess,
            ::onMarketRequestError
        )
    }

    private fun onMarketRequestSuccess(markets: List<MarketRequest>) {
        _state.update { requestUiState ->
            requestUiState.copy(
                isLoading = false,
                marketsUpdated = markets.map { it.toMarketRequestUiState() },
                markets = markets.map { it.toMarketRequestUiState() }
            )
        }
        onGetMarkets()
    }

    private fun onMarketRequestError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error, isError = true) }
        when (error) {
            is ErrorHandler.NoConnection -> {
                _state.update { it.copy(isError = true) }
            }

            ErrorHandler.UnAuthorizedUser -> {
                effectActionExecutor(_effect, MarketsUiEffect.UnAuthorizedUserEffect)
            }

            else -> {}
        }
    }

    override fun updateMarket(marketId: Int, isApproved: Boolean) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { adminMarketsManager.updateMarket(marketId.toLong(), isApproved) },
            ::onUpdateMarketSuccess,
            ::onUpdateMarketError
        )
    }

    private fun onUpdateMarketSuccess(isApproved: Boolean) {
        _state.update { it.copy(isLoading = false) }
        getMarkets()
    }

    private fun onUpdateMarketError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    private fun onGetMarkets() {
        val requestState = _state.value.marketsState
        _state.update { uiState ->
            uiState.copy(
                marketsState = requestState, selectedMarket = null,
                marketsUpdated = getMarketsOnState(requestState)
            )
        }
    }

    private fun getMarketsOnState(state: MarketsState): List<MarketRequestUiState> {
        return when (state) {
            MarketsState.APPROVED -> _state.value.markets.filter { it.isApproved }
            MarketsState.UNAPPROVED -> _state.value.markets.filter { !it.isApproved }
            MarketsState.ALL -> _state.value.markets
        }
    }

    override fun onClickMarketsState(state: MarketsState) {
        _state.update {
            it.copy(
                marketsState = state,
                marketsUpdated = getMarketsOnState(state),
                selectedMarket = null
            )
        }
    }

    override fun onClickTryAgain() {
        getMarkets()
    }

    override fun onClickMarket(position: Int) {
        val updatedRequests = _state.value.marketsUpdated.mapIndexed { index, market ->
            market.copy(
                isSelected = index == position,
                state = if (market.isApproved) MarketsState.APPROVED else MarketsState.UNAPPROVED
            )
        }
        _state.update {
            it.copy(
                marketsUpdated = updatedRequests,
                selectedMarket = updatedRequests[position]
            )
        }
    }

    override fun onClickCancel(marketId: Int) {
        updateMarket(marketId, false)
    }

    override fun onClickApprove(marketId: Int) {
        updateMarket(marketId, true)
    }

}
