package org.the_chance.honeymart.ui.feature.market

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllMarketUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketUseCase,
) : BaseViewModel<MarketsUiState>(MarketsUiState()), MarketInteractionListener {
     val uiMarketState = MutableLiveData<Long>()
    override val TAG: String = "TAG"

    init {
        getAllMarkets()
    }

    private fun getAllMarkets() {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllMarket() },
            { market -> market.asMarketsUiState() },
            ::onSuccess,
            ::onError
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
                isError = false,
                markets = markets
            )
        }
    }

    override fun onClickMarket(id: Long) {
            uiMarketState.postValue(id)
    }

}


