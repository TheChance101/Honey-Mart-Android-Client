package org.the_chance.honeymart.ui.features.markets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.HoneyMartTitle
import org.the_chance.honeymart.ui.composables.NavigationHandler
import org.the_chance.honeymart.ui.features.login.navigateToLogin
import org.the_chance.honeymart.ui.features.markets.composables.EmptyPlaceholder
import org.the_chance.honeymart.ui.features.markets.composables.ItemMarketRequest
import org.the_chance.honeymart.ui.features.markets.composables.MarketRequestDetails
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun MarketsScreen(viewModel: MarketsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                MarketsUiEffect.UnAuthorizedUserEffect -> navController.navigateToLogin()
            }
        })
    RequestsContent(state, viewModel)
}

@Composable
fun RequestsContent(
    state: MarketsRequestUiState,
    listener: MarketsInteractionListener,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HoneyMartTitle()
        ContentVisibility(state = state.isContentScreenVisible()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.dimens.space16),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16))
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space20),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                    ) {
                        CustomChip(
                            state = state.marketsState == MarketsState.ALL,
                            text = stringResource(R.string.all_markets),
                            onClick = { listener.onClickMarketsState(MarketsState.ALL) }
                        )
                        CustomChip(
                            state = state.marketsState == MarketsState.UNAPPROVED,
                            text = stringResource(R.string.pending),
                            onClick = { listener.onClickMarketsState(MarketsState.UNAPPROVED) }
                        )
                        CustomChip(
                            state = state.marketsState == MarketsState.APPROVED,
                            text = stringResource(R.string.approved),
                            onClick = { listener.onClickMarketsState(MarketsState.APPROVED) }
                        )
                    }
                    Loading(state = state.isLoading &&state.markets.isNotEmpty())
                    LazyColumn(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        itemsIndexed(state.markets) { index, item ->
                            ItemMarketRequest(
                                onClickCard = { listener.onClickMarket(index) },
                                ownerName = item.ownerName,
                                marketName = item.marketName,
                                ownerNameFirstCharacter = item.ownerNameFirstCharacter(),
                                onCardSelected = item.isSelected,
                                marketStateText = item.marketStateText,
                                state = item.isApproved,
                                marketsState = state.marketsState,
                                isLoading = state.isLoading
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    state.selectedMarket?.let {
                        MarketRequestDetails(
                            state = state.selectedMarket.state == MarketsState.UNAPPROVED,
                            request = state.selectedMarket, listener = listener,
                            imageUrl = it.imageUrl
                        )
                    }
                }
            }
        }
    }
    Loading(state = state.isLoading &&state.markets.isEmpty())
    ConnectionErrorPlaceholder(
        state = state.isError,
        onClickTryAgain = { listener.onClickTryAgain() })
    EmptyPlaceholder(state = state.emptyRequestsPlaceHolder())
}