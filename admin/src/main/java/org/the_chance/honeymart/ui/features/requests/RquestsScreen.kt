package org.the_chance.honeymart.ui.features.requests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.HoneyMartTitle
import org.the_chance.honeymart.ui.features.requests.composables.RequestDetails
import org.the_chance.honeymart.ui.features.requests.composables.Requests
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        HoneyMartTitle()
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.dimens.space40),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Requests(state, listener)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                ContentVisibility(state = state.isRequestSelected) {
                    RequestDetails(state, listener)
                }
            }
        }
    }
}