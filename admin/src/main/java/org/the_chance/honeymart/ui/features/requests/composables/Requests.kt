package org.the_chance.honeymart.ui.features.requests.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.requests.RequestsInteractionListener
import org.the_chance.honeymart.ui.features.requests.RequestsUiState
import org.the_chance.honeymart.ui.features.requests.allRequests
import org.the_chance.honeymart.ui.features.requests.approved
import org.the_chance.honeymart.ui.features.requests.newRequests
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun Requests(
    state: RequestsUiState,
    listener: RequestsInteractionListener
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space20),
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
        ) {
            CustomChip(
                state = state.allRequests(),
                text = stringResource(R.string.all_requests),
                onClick = listener::onClickAllRequests
            )
            CustomChip(
                state = state.newRequests(),
                text = stringResource(R.string.new_requests),
                onClick = listener::onClickNewRequests
            )
            CustomChip(
                state = state.approved(),
                text = stringResource(R.string.approved),
                onClick = listener::onClickApproved
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state.requests.size) { index ->
                val request = state.requests[index]
                ItemRequest(
                    onClickCard = listener::onClickRequest,
                    userName = request.ownerName,
                    marketName = request.marketName,
                    date = request.date,
                    image = request.ownerImage,
                    ownerNameFirstCharacter = state.ownerNameFirstCharacter,
                    onCardSelected = request.isRequestSelected,
                    isRequestNew = request.isRequestNew
                )
            }
        }
    }
}