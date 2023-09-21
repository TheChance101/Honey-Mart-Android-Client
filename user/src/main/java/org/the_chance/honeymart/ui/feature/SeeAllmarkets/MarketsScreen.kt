package org.the_chance.honeymart.ui.feature.SeeAllmarkets

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
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EventHandler
import org.the_chance.honeymart.ui.composables.HoneyAppBarScaffold
import org.the_chance.honeymart.ui.composables.PagingLoading
import org.the_chance.honeymart.ui.feature.SeeAllmarkets.compoaseable.MarketItem
import org.the_chance.honeymart.ui.feature.marketInfo.navigateToCategoryScreen
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun MarketsScreen(
    viewModel: MarketViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value

    EventHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                is MarketUiEffect.ClickMarketEffect -> navController.navigateToCategoryScreen(effect.marketId)
            }
        })

    MarketContent(state = state, listener = viewModel)
}

@Composable
fun MarketContent(
    state: MarketsUiState,
    listener: MarketInteractionListener,
) {
    HoneyAppBarScaffold {
        val markets = state.markets
        Loading(state = state.isLoading && state.isMarketsEmpty)
        ContentVisibility(state = state.contentScreen()) {
            LazyColumn(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary),
                state = rememberLazyListState(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                contentPadding = PaddingValues(
                    horizontal = MaterialTheme.dimens.space16,
                    vertical = MaterialTheme.dimens.space8
                ),
            ) {
                items(markets.size) { index ->
                    listener.onChangeMarketsScrollPosition(index)
                    val market = markets[index]
                    MarketItem(
                        onClickItem = listener::onClickMarket,
                        marketId = market.marketId,
                        marketImage = market.marketImage,
                        marketName = market.marketName
                    )
                }
                item {
                    PagingLoading(state = state.isLoading && !state.isMarketsEmpty)
                }
            }
        }
        ConnectionErrorPlaceholder(
            state = state.isError,
            onClickTryAgain = listener::getAllMarkets
        )
    }
}