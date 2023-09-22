package org.the_chance.honeymart.ui.features.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.components.EmptyPlaceholder
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.features.notifications.composables.AllNotificationsContent
import org.the_chance.honeymart.ui.features.notifications.composables.OrderDetails
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(lifecycleOwner) {
        viewModel.getAllNotifications(NotificationStates.NEW.state, NotificationStates.NEW)
    }
    NotificationsContent(state, viewModel)
}

@Composable
fun NotificationsContent(
    state: NotificationsUiState,
    listener: NotificationsInteractionListener,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HoneyMartTitle()
        Loading(state = state.isLoading && state.notifications.isEmpty() && state.all())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            EmptyPlaceholder(
                state = state.notifications.isEmpty() && state.all(),
                emptyObjectName = stringResource(id = R.string.notifications_label),
                notificationState = true
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.dimens.space16),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                AllNotificationsContent(state = state, listener = listener)

            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                ContentVisibility(state = !state.isLoading && state.notifications.isNotEmpty()) {
                    OrderDetails(state)
                }
                Loading(state = state.isLoading)
            }
        }
    }


}