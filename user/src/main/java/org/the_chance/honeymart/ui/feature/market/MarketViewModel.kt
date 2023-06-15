package org.the_chance.honeymart.ui.feature.market

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetAllMarketsUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.MarketUiState
import org.the_chance.honeymart.ui.feature.uistate.MarketsUiState
import org.the_chance.honeymart.ui.feature.uistate.toMarketUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketsUseCase,
) : BaseViewModel<MarketsUiState, Long>(MarketsUiState()), MarketInteractionListener {

    override val TAG: String = this::class.java.simpleName

    init {
        getAllMarkets()
    }

    private fun getAllMarkets() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllMarket().map { it.toMarketUiState() } },
            ::onGetMarketSuccess,
            ::onGetMarketError
        )
    }

    private fun onGetMarketError(throwable: Throwable) {
        this._state.update {
            it.copy(
                isLoading = false,
                isError  = true,
            )
        }
    }

    private fun onGetMarketSuccess(markets: List<MarketUiState>) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                markets = markets
            )
        }
    }

    override fun onClickMarket(marketId: Long) {
        viewModelScope.launch { _effect.emit(EventHandler(marketId)) }
    }
}