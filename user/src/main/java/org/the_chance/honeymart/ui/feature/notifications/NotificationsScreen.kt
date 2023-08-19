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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honeymart.ui.feature.notifications.composable.NotificationArrivedCard
import org.the_chance.honeymart.ui.feature.notifications.composable.NotificationCard
import org.the_chance.honeymart.ui.feature.notifications.composable.NotificationSuccessCard
import org.the_chance.honeymart.ui.feature.notifications.composable.StateItem
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Loading(state = state.isLoading)
            ConnectionErrorPlaceholder(
                state = state.isError,
                onClickTryAgain = {},
            )
            EmptyOrdersPlaceholder(
                state = state.emptyNotificationsPlaceHolder(),
                image = R.drawable.placeholder_wish_list,
                title = stringResource(R.string.your_notifications_is_empty),
                subtitle = stringResource(R.string.you_ll_receive_a_notification_after_placing_your_order),
                onClickDiscoverMarkets = {},
                visibility = false
            )
            ContentVisibility(state = state.screenContent()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier
                        .padding(top = MaterialTheme.dimens.space24)
                ) {
                    StateItem(
                        painter = painterResource(R.drawable.ic_notification),
                        color = if (state.all()) {
                            primary100
                        } else {
                            black37
                        },
                        text = stringResource(R.string.all),
                        onClickState = listener::onClickAll
                    )
                    StateItem(
                        painter = painterResource(R.drawable.icon_order_nav),
                        color = if (state.order()) {
                            primary100
                        } else {
                            black37
                        },
                        text = stringResource(R.string.order_title),
                        onClickState = listener::onClickOrder
                    )
                    StateItem(
                        painter = painterResource(R.drawable.ic_delivery),
                        color = if (state.delivery()) {
                            primary100
                        } else {
                            black37
                        },
                        text = stringResource(R.string.delivery),
                        onClickState = listener::onClickDelivery
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(white)
                        .fillMaxSize()
                ) {
                    items(4) {
                        if (state.all()) {
                            NotificationSuccessCard()
                            NotificationArrivedCard()
                        } else {
                            NotificationCard(
                                painter = if (state.order()) {
                                    painterResource(R.drawable.icon_order_nav)
                                } else {
                                    painterResource(R.drawable.ic_delivery)
                                },
                                title = if (state.order()) {
                                    "Order Success"
                                } else {
                                    "Order Arrived"
                                },
                                date = if (state.order()) {
                                    "12:30"
                                } else {
                                    "1 Aug, 2023"
                                },
                                message = if (state.order()) {
                                    "Order #2453 has been success.\n" +
                                            "Please wait for the product to be sent"
                                } else {
                                    "Order #46567 has been Completed &\n" +
                                            "and arrived at the destination address."
                                },
                            )
                        }
                    }
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