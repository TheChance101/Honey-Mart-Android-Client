package org.the_chance.honeymart.ui.feature.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honeymart.ui.feature.home.navigateToHomeScreen
import org.the_chance.honeymart.ui.feature.notifications.composable.NotificationCard
import org.the_chance.honeymart.ui.feature.notifications.composable.StateItem
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current
    LaunchedEffect(true) {
        viewModel.effect.collect {
            when (it) {
                NotificationsUiEffect.OnClickTryAgain -> navController.navigateToNotificationsScreen()
                NotificationsUiEffect.OnClickDiscoverMarket -> navController.navigateToHomeScreen()
            }
        }
    }


    NotificationsContent(state = state, listener = viewModel)
}

@Composable
fun NotificationsContent(
    state: NotificationsUiState,
    listener: NotificationsInteractionListener,
) {
    AppBarScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space24)
        ) {
            Loading(state = state.isLoading)
            ConnectionErrorPlaceholder(
                state = state.isError,
                onClickTryAgain = listener::onClickTryAgain,
            )
            EmptyOrdersPlaceholder(
                state = state.emptyNotificationsPlaceHolder(),
                image = R.drawable.placeholder_wish_list,
                title = stringResource(R.string.your_notifications_is_empty),
                subtitle = stringResource(R.string.you_ll_receive_a_notification_after_placing_your_order),
                onClickDiscoverMarkets = listener::onClickDiscoverMarket,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space32),
                modifier = Modifier
                    .padding(top = MaterialTheme.dimens.space24)
            ) {
                StateItem(
                    painter = painterResource(R.drawable.ic_notification),
                    color = if (state.all()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    },
                    text = stringResource(R.string.all),
                    onClickState = listener::onGetAllNotifications
                )
                StateItem(
                    painter = painterResource(R.drawable.icon_order_nav),
                    color = if (state.order()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    },
                    text = stringResource(R.string.order_title),
                    onClickState = listener::onGetOrderNotifications
                )
                StateItem(
                    painter = painterResource(R.drawable.ic_delivery),
                    color = if (state.delivery()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    },
                    text = stringResource(R.string.delivery),
                    onClickState = listener::onGetDeliveryNotifications
                )
            }
            LazyColumn(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.dimens.space16)
                    .background(MaterialTheme.colorScheme.secondary)
                    .clip(RoundedCornerShape(MaterialTheme.dimens.space24))
            ) {
                items(state.updatedNotifications.size) {
                    val notification = state.updatedNotifications[it]
                    NotificationCard(
                        painter =
                        when {
                            state.order() -> {
                                painterResource(R.drawable.icon_order_nav)
                            }

                            state.delivery() -> {
                                painterResource(R.drawable.ic_delivery)
                            }

                            else -> {
                                painterResource(id = R.drawable.ic_notification)
                            }
                        },
                        title = notification.title,
                        date = convertDate(notification),
                        message = notification.body,
                        index = it
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun NotificationsPreview() {
    NotificationsScreen()
}