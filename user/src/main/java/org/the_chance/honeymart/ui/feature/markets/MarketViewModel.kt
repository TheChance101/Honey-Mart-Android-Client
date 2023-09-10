package org.the_chance.honeymart.ui.feature.markets

import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.Market
import org.the_chance.honeymart.domain.usecase.GetAllMarketsPagingUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketsPagingUseCase,
) : BaseViewModel<MarketsUiState, MarketUiEffect>(MarketsUiState()),
    MarketInteractionListener {

    override val TAG: String = this::class.java.simpleName

    init {
        getAllMarkets()
    }

    override fun getAllMarkets() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecutePaging(
            { getAllMarket() },
            ::onGetMarketSuccess,
            ::onGetMarketError
        )
    }

    private fun onGetMarketSuccess(markets: PagingData<Market>) {
        val mappedMarkets = markets.map { it.toMarketUiState() }
        _state.update {
            it.copy(
                isLoading = false,
                markets = flowOf(mappedMarkets)
            )
        }
    }

    private fun onGetMarketError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    override fun onClickMarket(marketId: Long) {
        effectActionExecutor(_effect, MarketUiEffect.ClickMarketEffect(marketId))
    }

    override fun onclickTryAgainMarkets() {
        getAllMarkets()
    }
}