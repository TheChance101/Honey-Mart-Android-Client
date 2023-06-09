package org.the_chance.honeymart.ui.feature.market

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetAllMarketsUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.MarketUiState
import org.the_chance.honeymart.ui.feature.uistate.MarketsUiState
import org.the_chance.honeymart.ui.feature.uistate.asMarketUiState
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getMarkets: GetAllMarketsUseCase,
) : BaseViewModel<MarketsUiState>(MarketsUiState()), MarketInteractionListener {
    override val TAG: String = "TAG"

    init {
        getAllMarkets()
        viewModelScope.launch {
            log("dataaa : ${getMarkets()}")
        }
    }

    private fun getAllMarkets() {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getMarkets() },
            { market -> market.asMarketUiState() },
            ::onSuccess,
            ::onError,
        )
    }

    private fun onError() {
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
            )
        }
    }

    private fun onSuccess(markets: List<MarketUiState>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                markets = markets
            )
        }
    }

    override fun onClickMarket(marketId: Long) {}

}