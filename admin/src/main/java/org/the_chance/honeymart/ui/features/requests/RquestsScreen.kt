package org.the_chance.honeymart.ui.features.requests

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.HoneyMartTitle
import org.the_chance.honeymart.ui.features.requests.composables.EmptyPlaceholder
import org.the_chance.honeymart.ui.features.requests.composables.ItemRequest
import org.the_chance.honeymart.ui.features.requests.composables.RequestDetails
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun RequestsScreen(viewModel: RequestsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    RequestsContent(state, viewModel)
}

@Composable
fun RequestsContent(
    state: RequestsUiState,
    listener: RequestsInteractionListener,
) {
    Loading(state = state.isLoading)
    ConnectionErrorPlaceholder(state = state.isError,
        onClickTryAgain =listener::onGetAllRequests )
    EmptyPlaceholder(state = state.emptyRequestsPlaceHolder())
    ContentVisibility(state = state.contentScreen()) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            HoneyMartTitle()
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = MaterialTheme.dimens.space40),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space20)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                    ) {
                        CustomChip(
                            state = false,
                            text = stringResource(R.string.all_requests),
                            onClick = listener::onGetAllRequests
                        )
                        CustomChip(
                            state = true,
                            text = stringResource(R.string.approved),
                            onClick = listener::onGetApproved
                        )
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(state.requests) { index,item ->
                            ItemRequest(
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
                        RequestDetails(state.selectedRequest, listener)
                }
            }
        }
    }
}