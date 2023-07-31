package org.the_chance.honeymart.ui.feature.market


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.category.navigateToCategoryScreen
import org.the_chance.honeymart.ui.feature.uistate.MarketsUiState
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.ContentVisibility
import org.the_chance.honymart.ui.composables.Loading


@Composable
fun MarketScreen(
    viewModel: MarketViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val navController = LocalNavigationProvider.current
    MarketContent(
        state = state,
        marketInteractionListener = navController::navigateToCategoryScreen
    )
}

@Composable
fun MarketContent(
    state: MarketsUiState,
    marketInteractionListener: (Long) -> Unit,
) {
    Loading(state.isLoading)

    ConnectionErrorPlaceholder(state = state.isError, onClickTryAgain = {})

    ContentVisibility(state = state.markets.isNotEmpty()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(state.markets.size) { position ->
                val market = state.markets[position]
                MarketItem(
                    market,
                    onClickItem = marketInteractionListener
                )
            }
        }
    }
}
