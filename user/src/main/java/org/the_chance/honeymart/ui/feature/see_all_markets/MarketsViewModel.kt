package org.the_chance.honeymart.ui.feature.see_all_markets

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.model.Market
import org.the_chance.honeymart.domain.usecase.GetAllMarketsPagingUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MarketsViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketsPagingUseCase,
) : BaseViewModel<MarketsUiState, MarketUiEffect>(MarketsUiState()),
    MarketInteractionListener {

    override val TAG: String = this::class.java.simpleName

    init {
        getAllMarkets()
    }

    override fun getAllMarkets() {
        _state.update {
            it.copy(
                isLoading = true,
                isError = false,
                error = null
            )
        }
        viewModelScope.launch {
            tryToExecute(
                { getAllMarket(state.value.page)!! },
                ::allMarketsSuccess,
                ::allMarketError
            )
        }
    }

    private fun allMarketsSuccess(markets: List<Market>) {
        _state.update {
            it.copy(
                isLoading = false,
                error = null,
                markets = it.markets.toMutableList().apply {
                    this.addAll(markets.map { it.toMarketUiState() })
                }
            )
        }
    }

    private fun allMarketError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error, isError = true) }
    }

    override fun onChangeMarketsScrollPosition(position: Int) {
        if (position + 1 >= state.value.page * MAX_PAGE_SIZE) {
            _state.update { it.copy(page = it.page + 1) }
            getAllMarkets()
        }
    }

    override fun onClickMarket(marketId: Long) {
        effectActionExecutor(_effect, MarketUiEffect.ClickMarketEffect(marketId))
    }

    override fun onclickTryAgainMarkets() {
        getAllMarkets()
    }

    companion object {
        const val MAX_PAGE_SIZE = 10
    }
}