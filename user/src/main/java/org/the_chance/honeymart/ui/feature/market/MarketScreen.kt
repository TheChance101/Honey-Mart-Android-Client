package org.the_chance.honeymart.ui.feature.market

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.honeymart.ui.feature.uistate.MarketUiState
import org.the_chance.honeymart.ui.feature.uistate.MarketsUiState


@Composable
fun MarketScreen(
    viewModel: MarketViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    MarketContent(
        state = state,
        marketInteractionListener = viewModel
    )
}

@Composable
fun MarketContent(
     state : MarketsUiState,
     marketInteractionListener : MarketInteractionListener
) {

    when{

        state.isLoading -> {
            //LoadingAnimation()
        }

        state.markets.isNotEmpty() -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ){
                items (state.markets.size){position ->
                    val market = state.markets[position]
                    MarketItem(
                        market,
                        onClickItem = {marketInteractionListener.onClickMarket(market.marketId!!)}
                    )
                }
            }
        }

        state.isError -> {

        }

    }

}
