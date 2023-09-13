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
import androidx.paging.compose.collectAsLazyPagingItems
import org.the_chance.honeymart.ui.composables.EventHandler
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.HoneyAppBarScaffold
import org.the_chance.honeymart.ui.composables.PagingStateVisibility
import org.the_chance.honeymart.ui.feature.marketInfo.navigateToCategoryScreen
import org.the_chance.honeymart.ui.feature.SeeAllmarkets.compoaseable.MarketItem
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
        val markets = state.markets.collectAsLazyPagingItems()
        ContentVisibility(state = markets.itemCount > 0) {
            LazyColumn(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary),
                state = rememberLazyListState(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                contentPadding = PaddingValues(
                    horizontal = MaterialTheme.dimens.space16,
                    vertical = MaterialTheme.dimens.space8
                ),
            ) {
                items(markets.itemCount) { position ->
                    val market = markets[position]
                    if (market != null) {
                        MarketItem(
                            onClickItem = listener::onClickMarket,
                            marketId = market.marketId,
                            marketImage = market.marketImage,
                            marketName = market.marketName
                        )
                    }
                }
                item {
                    PagingStateVisibility(markets)
                }
            }
        }
        ConnectionErrorPlaceholder(
            state = state.isError,
            onClickTryAgain = listener::getAllMarkets
        )
        Loading(state.isLoading)
    }
}
