package org.the_chance.honeymart.ui.feature.market

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetAllMarketUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.market.uistate.MarketUiState
import org.the_chance.honeymart.ui.feature.market.uistate.MarketsUiState
import org.the_chance.honeymart.ui.feature.market.uistate.asMarketsUiState
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketUseCase,
) : BaseViewModel(), MarketInteractionListener {

    override val TAG: String = "TAG"

    private val _uiState = MutableStateFlow(MarketsUiState())
    val uiState: StateFlow<MarketsUiState> = this._uiState


    init {
        getAllMarkets()
    }

    private fun getAllMarkets() {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllMarket() },
            transform = { market -> market.asMarketsUiState() },
            this::onSuccess, ::onError
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

    private fun onSuccess(
        markets: List<MarketUiState>,
    ) {
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                markets = markets
            )
        }
    }

    override fun onClickMarket(id: Int) {
        // navigate to market detail
    }


}


