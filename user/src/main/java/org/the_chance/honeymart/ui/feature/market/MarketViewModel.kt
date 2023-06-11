package org.the_chance.honeymart.ui.feature.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetAllMarketsUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.MarketUiState
import org.the_chance.honeymart.ui.feature.uistate.MarketsUiState
import org.the_chance.honeymart.ui.feature.uistate.asMarketUiState
import org.the_chance.honeymart.ui.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketsUseCase,
) : BaseViewModel<MarketsUiState>(MarketsUiState()), MarketInteractionListener {

    override val TAG: String = this::class.java.simpleName

    private val _marketUiEffect = MutableSharedFlow<EventHandler<Long>>()
    val marketUiEffect = _marketUiEffect.asSharedFlow()

    init {
        getAllMarkets()
    }

    private fun getAllMarkets() {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllMarket() },
            { market -> market.asMarketUiState() },
            ::onSuccess,
            ::onError
        )
    }


    private fun onError(throwable: Throwable) {
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
                isError = false,
                markets = markets
            )
        }
    }

    override fun onClickMarket(marketId: Long) {
//        _uiMarketState.postValue(EventHandler(marketId))
        viewModelScope.launch {
            _marketUiEffect.emit(EventHandler(marketId))
        }
    }
}