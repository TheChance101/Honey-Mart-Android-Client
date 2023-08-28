package org.the_chance.honeymart.ui.features.markets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.HoneyMartTitle
import org.the_chance.honeymart.ui.features.markets.composables.EmptyPlaceholder
import org.the_chance.honeymart.ui.features.markets.composables.ItemMarketRequest
import org.the_chance.honeymart.ui.features.markets.composables.MarketRequestDetails
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun MarketsScreen(viewModel: MarketsViewModel = hiltViewModel()) {
    val navController = LocalNavigationProvider.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                MarketsUiEffect.UnAuthorizedUserEffect ->  navController.navigateToLogin()
                else -> {}
            }
        }
    }

    RequestsContent(state, viewModel)
}

@Composable
fun RequestsContent(
    state: RequestsUiState,
    listener: MarketsInteractionListener,
) {
        Column(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            HoneyMartTitle()
            Row(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space40),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
            ) {
                CustomChip(
                    state = state.requestsStates == RequestsStates.UNAPPROVED,
                    text = stringResource(R.string.pending),
                    onClick = { listener.onGetFilteredRequests(false) }
                )
                CustomChip(
                    state = state.requestsStates == RequestsStates.APPROVED,
                    text = stringResource(R.string.approved),
                    onClick = { listener.onGetFilteredRequests(true) }
                )
            }
            ContentVisibility(state = state.contentScreen()) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = MaterialTheme.dimens.space40)
                    .padding(top = MaterialTheme.dimens.space24),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space20)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(state.requests) { index,item ->
                            ItemMarketRequest(
                                onClickCard = { listener.onClickRequest(index) },
                                ownerName = item.ownerName,
                                marketName = item.marketName,
                                ownerNameFirstCharacter = item.ownerNameFirstCharacter(),
                                onCardSelected = item.isSelected,
                                isRequestNew = item.isRequestNew
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize().weight(1f)
                ) {
                    MarketRequestDetails(
                        state = state.requestsStates == RequestsStates.UNAPPROVED,
                        request = state.selectedRequest, listener = listener)
                }
            }
        }
    }
    Loading(state = state.isLoading)
    ConnectionErrorPlaceholder(state = state.isError,
        onClickTryAgain = { listener.onGetFilteredRequests(false) } )
    EmptyPlaceholder(state = state.emptyRequestsPlaceHolder())
}