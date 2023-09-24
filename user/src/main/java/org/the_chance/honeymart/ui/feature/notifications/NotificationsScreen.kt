package org.the_chance.honeymart.ui.feature.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honeymart.ui.composables.HoneyAppBarScaffold
import org.the_chance.honeymart.ui.feature.home.navigateToHomeScreen
import org.the_chance.honeymart.ui.feature.notifications.composable.NotificationCard
import org.the_chance.honeymart.ui.feature.notifications.composable.StateItem
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
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
                NotificationsUiEffect.OnClickDiscoverMarket -> navController.navigateToHomeScreen()
                NotificationsUiEffect.OnClickNotification -> navController.navigateToNotificationsScreen()
            }
        }
    }


    NotificationsContent(state = state, listener = viewModel)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationsContent(
    state: NotificationsUiState,
    listener: NotificationsInteractionListener,
) {
    HoneyAppBarScaffold {
        val pullRefreshState = rememberPullRefreshState(
            refreshing = state.isRefresh,
            onRefresh = listener::onRefresh
        )
        Box(contentAlignment = Alignment.TopCenter) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Loading(state = state.isLoading)
                ConnectionErrorPlaceholder(
                    state = state.isError,
                    onClickTryAgain = listener::onClickTryAgain,
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
                        color = if (state.processing()) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        },
                        text = stringResource(R.string.processing),
                        onClickState = listener::onGetProcessingNotifications
                    )
                    StateItem(
                        painter = painterResource(R.drawable.ic_delivery),
                        color = if (state.completed()) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        },
                        text = stringResource(R.string.delivery),
                        onClickState = listener::onGetCompletedNotifications
                    )
                }
                EmptyOrdersPlaceholder(
                    state = state.emptyNotificationsPlaceHolder(),
                    image = R.drawable.placeholder_wish_list,
                    title = stringResource(R.string.your_notifications_is_empty),
                    subtitle = stringResource(R.string.you_ll_receive_a_notification_after_placing_your_order),
                    onClickDiscoverMarkets = listener::onClickDiscoverMarket,
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            top = MaterialTheme.dimens.space24,
                            bottom = MaterialTheme.dimens.space16
                        )
                        .clip(RoundedCornerShape(MaterialTheme.dimens.space24))
                        .background(MaterialTheme.colorScheme.onTertiary),
                ) {
                    items(state.updatedNotifications.size) {
                        val notification = state.updatedNotifications[it]
                        NotificationCard(
                            painter = if (notification.columnIcon()) {
                                painterResource(R.drawable.icon_order_nav)
                            } else {
                                painterResource(R.drawable.ic_delivery)
                            },
                            title = notification.title,
                            date = notification.date,
                            message = notification.body,
                            index = it
                        )
                    }

                }
            }
            PullRefreshIndicator(
                refreshing = state.isRefresh,
                state = pullRefreshState,
                contentColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}