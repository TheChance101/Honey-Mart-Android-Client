package org.the_chance.honeymart.ui.features.notifications.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.EmptyPlaceholder
import org.the_chance.honeymart.ui.features.notifications.NotificationStates
import org.the_chance.honeymart.ui.features.notifications.NotificationsInteractionListener
import org.the_chance.honeymart.ui.features.notifications.NotificationsUiState
import org.the_chance.honeymart.ui.features.notifications.all
import org.the_chance.honeymart.ui.features.notifications.cancelled
import org.the_chance.honeymart.ui.features.notifications.new
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllNotificationsContent(
    state: NotificationsUiState,
    listener: NotificationsInteractionListener,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = MaterialTheme.dimens.space16
                ),
            contentPadding = PaddingValues(
                end = MaterialTheme.dimens.space16,
            ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {

            item {
                CustomChip(
                    state = state.all(),
                    text = stringResource(R.string.all),
                    onClick = {
                        listener.getAllNotifications(
                            NotificationStates.ALL.state, NotificationStates.ALL
                        )
                    }
                )
            }

            item {
                CustomChip(
                    state = state.new(),
                    text = "New",
                    onClick = {
                        listener.getAllNotifications(
                            NotificationStates.NEW.state,
                            NotificationStates.NEW
                        )
                    }
                )
            }

            item {
                CustomChip(
                    state = state.cancelled(),
                    text = "Cancelled",
                    onClick = {
                        listener.getAllNotifications(
                            NotificationStates.CANCELLED.state,
                            NotificationStates.CANCELLED
                        )
                    }
                )
            }
        }
        val pullRefreshState = rememberPullRefreshState(
            refreshing = state.isRefresh,
            onRefresh = listener::onRefresh
        )
        if (state.isRefresh){
            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                refreshing = state.isRefresh,
                state = pullRefreshState,
                contentColor = MaterialTheme.colorScheme.primary
            )
        }
        Column(modifier = Modifier.fillMaxWidth().pullRefresh(pullRefreshState)) {
            EmptyPlaceholder(
                state = state.notifications.isEmpty() && (state.new() || state.cancelled()),
                emptyObjectName = stringResource(id = R.string.notifications_label),
                notificationText = stringResource(R.string.receive_notification_cancels)
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space16)
            ) {
                items(state.notifications.size) {
                    val notification = state.notifications[it]
                    NotificationCard(
                        onClickCard = {
                            listener.onCLickNotificationCard(state.orderDetails, notification)
                        },
                        isSelected = notification.isNotificationSelected,
                        date = notification.date,
                        state = state,
                        orderId = notification.orderId.toString(),
                        notificationBody = notification.body,
                        notificationTitle = notification.title
                    )
                }
            }
        }
    }
}