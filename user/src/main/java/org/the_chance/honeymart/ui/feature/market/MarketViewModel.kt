package org.the_chance.ui.market

//import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetAllMarketUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.market.MarketInteractionListener
import org.the_chance.honeymart.ui.feature.market.uistate.MarketsUiState
import org.the_chance.honeymart.ui.feature.market.uistate.asMarketsUiState
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketUseCase,
) : BaseViewModel(), MarketInteractionListener {
    override val TAG: String = "TAG"

    private val _marketUiState = MutableStateFlow(MarketsUiState())
    val marketUiState: StateFlow<MarketsUiState> = _marketUiState

    init {
        getAllMarkets()
    }

    private fun getAllMarkets() {
        _marketUiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val markets = getAllMarket().map { it.asMarketsUiState() }
                _marketUiState.update {
                    it.copy(
                        markets = markets,
                        isLoading = false,
                        isError = false,
                    )
                }
            } catch (e: Throwable) {
                _marketUiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                    )
                }
            }
        }
    }

    override fun onClickMarket(id: Int) {
        // navigate to market detail
    }


}


