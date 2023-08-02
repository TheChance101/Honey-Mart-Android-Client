package org.the_chance.honeymart.ui.feature.market


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.category.navigateToCategoryScreen
import org.the_chance.honeymart.ui.feature.uistate.MarketsUiState
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.ContentVisibility
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun MarketScreen(
    viewModel: MarketViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val navController = LocalNavigationProvider.current
    MarketContent(
        state = state,
        onClickMarket = navController::navigateToCategoryScreen,
        listener = viewModel
    )
}

@Composable
fun MarketContent(
    state: MarketsUiState,
    onClickMarket: (Long) -> Unit,
    listener: MarketInteractionListener,
) {
    AppBarScaffold {
        ContentVisibility(state = state.markets.isNotEmpty() && !state.isError) {
            LazyColumn(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary),
                state = rememberLazyListState(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                contentPadding = PaddingValues(
                    horizontal = MaterialTheme.dimens.space16,
                    vertical = MaterialTheme.dimens.space8
                ),
            ) {
                items(state.markets.size) { position ->
                    MarketItem(state.markets[position], onClickItem = onClickMarket)
                }
            }
        }
        ConnectionErrorPlaceholder(
            state = state.isError && state.markets.isEmpty(),
            onClickTryAgain = listener::getChosenMarkets
        )
        Loading(state.isLoading)
    }
}
