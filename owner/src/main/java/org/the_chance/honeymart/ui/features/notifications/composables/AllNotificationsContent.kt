package org.the_chance.honeymart.ui.features.notifications.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.components.Placeholder
import org.the_chance.honeymart.ui.features.notifications.NotificationsInteractionListener
import org.the_chance.honeymart.ui.features.notifications.NotificationsUiState
import org.the_chance.honeymart.ui.features.notifications.all
import org.the_chance.honeymart.ui.features.notifications.cancelled
import org.the_chance.honeymart.ui.features.notifications.new
import org.the_chance.honeymart.ui.features.orders.OrderStates
import org.the_chance.honeymart.ui.features.orders.OrdersInteractionsListener
import org.the_chance.honeymart.ui.features.orders.OrdersUiState
import org.the_chance.honeymart.ui.features.orders.all
import org.the_chance.honeymart.ui.features.orders.cancel
import org.the_chance.honeymart.ui.features.orders.composables.ItemOrder
import org.the_chance.honeymart.ui.features.orders.done
import org.the_chance.honeymart.ui.features.orders.emptyOrdersPlaceHolder
import org.the_chance.honeymart.ui.features.orders.pending
import org.the_chance.honeymart.ui.features.orders.processing
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens
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
                end = MaterialTheme.dimens.space16
            ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
        ) {
            item {
                CustomChip(
                    state = state.all(),
                    text = stringResource(R.string.all),
                    onClick = { }
                )
            }
            item {
                CustomChip(
                    state = state.new(),
                    text = stringResource(R.string.new_order),
                    onClick = {  }
                )
            }
            item {
                CustomChip(
                    state = state.cancelled(),
                    text = stringResource(id = R.string.processing),
                    onClick = {  }
                )
            }

        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
            contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space16)
        ) {
            items(
                items = state.notifications,
                key = { it.orderId }
            ) { notification ->
                NotificationCard(
                    onClickCard = {},
                    state = state,
                    isSelected = false,
                    imageUrl = notification.notificationId.toString(),
                    userName = notification.userId.toString(),
                    date =notification.date,
                    orderState = notification.title
                )
            }
        }
    }
}