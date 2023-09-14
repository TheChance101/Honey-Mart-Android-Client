package org.the_chance.honeymart.ui.feature.SeeAllmarkets

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
class MarketViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketsPagingUseCase,
) : BaseViewModel<MarketsUiState, MarketUiEffect>(MarketsUiState()),
    MarketInteractionListener {

    override val TAG: String = this::class.java.simpleName
    private var page = _state.value.page
    private var marketListScrollPosition = 0

    init {
        getAllMarkets()
    }

    override fun getAllMarkets() {
        resetSearchState()
        _state.update { it.copy(isLoading = true, isError = false) }
        viewModelScope.launch {
            val markets = getAllMarket(page)
            val mappedMarkets = markets!!.map { it.toMarketUiState() }
            _state.update { it.copy(markets = mappedMarkets, isLoading = false) }
        }
    }

    private fun appendMarkets(markets: List<MarketUiState>) {
        val current = ArrayList(state.value.markets)
        current.addAll(markets)
        _state.update { it.copy(markets = current) }
    }

    private fun incrementPage() {
        page += 1
    }

    fun onChangeMarketsScrollPosition(position: Int) {
        marketListScrollPosition = position
    }

    private fun resetSearchState() {
        _state.update { it.copy(markets = listOf()) }
        page = 1
        onChangeMarketsScrollPosition(0)
    }

    override fun onClickMarket(marketId: Long) {
        effectActionExecutor(_effect, MarketUiEffect.ClickMarketEffect(marketId))
    }

    override fun onclickTryAgainMarkets() {
        getAllMarkets()
    }

    override fun onScrollDown() {
        viewModelScope.launch {
            if ((marketListScrollPosition + 1) >= (page * MAX_PAGE_SIZE)) {
                _state.update { it.copy(isLoading = true) }
                incrementPage()
                if (page > 1) {
                    val result = getAllMarket(page)!!.map { it.toMarketUiState() }
                    appendMarkets(result)
                }
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    companion object {
        const val MAX_PAGE_SIZE = 10
    }
}