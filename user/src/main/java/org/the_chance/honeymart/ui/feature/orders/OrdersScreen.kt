package org.the_chance.honeymart.ui.feature.orders

import SwipeBackGround
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.market.navigateToMarketScreen
import org.the_chance.honeymart.ui.feature.order_details.navigateToOrderDetailsScreen
import org.the_chance.honeymart.ui.feature.orders.composable.CustomChip
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.ContentVisibility
import org.the_chance.honymart.ui.composables.CustomAlertDialog
import org.the_chance.honymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honymart.ui.composables.ItemOrder
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrdersScreen(
    viewModel: OrderViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val navController = LocalNavigationProvider.current

    LaunchedEffect(lifecycleOwner) {
        viewModel.getAllProcessingOrders()
    }

    OrdersContent(
        state = state,
        ordersInteractionsListener = viewModel,
        onClickItemOrder = navController::navigateToOrderDetailsScreen,
        navController = navController
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OrdersContent(
    state: OrdersUiState,
    ordersInteractionsListener: OrdersInteractionsListener,
    onClickItemOrder: (orderId: Long) -> Unit = {},
    navController: NavController
) {
    AppBarScaffold {
        ConnectionErrorPlaceholder(
            state = state.isError,
            onClickTryAgain = ordersInteractionsListener::getAllProcessingOrders
        )
        EmptyOrdersPlaceholder(
            state = state.orders.isEmpty() && !state.isError && !state.isLoading,
            image = R.drawable.placeholder_order,
            title = stringResource(R.string.placeholder_title),
            subtitle = stringResource(R.string.placeholder_subtitle),
            onClickDiscoverMarkets = { navController.navigateToMarketScreen() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = MaterialTheme.dimens.space24)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.dimens.space16,
                        vertical = MaterialTheme.dimens.space8
                    ),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
            ) {
                CustomChip(
                    state = state.orderStates == OrderStates.PROCESSING,
                    text = stringResource(id = R.string.processing),
                    onClick = ordersInteractionsListener::getAllProcessingOrders
                )
                CustomChip(
                    state = state.orderStates == OrderStates.DONE,
                    text = stringResource(id = R.string.done),
                    onClick = ordersInteractionsListener::getAllDoneOrders
                )
                CustomChip(
                    state = state.orderStates == OrderStates.CANCELED,
                    text = stringResource(id = R.string.cancel),
                    onClick = ordersInteractionsListener::getAllCancelOrders
                )
            }

            Loading(state = state.isLoading)

            ContentVisibility(state.orders.isNotEmpty() && !state.isError) {
                LazyColumn(
                    modifier = Modifier.padding(
                        start = MaterialTheme.dimens.space16,
                        end = MaterialTheme.dimens.space16,
                        top = MaterialTheme.dimens.space8
                    ),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                    contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space16)
                ) {
                    itemsIndexed(
                        items = state.orders,
                    ) { index, orderItem ->
                        var showDialog by remember { mutableStateOf(false) }
                        val dismissState = rememberDismissState(
                            confirmValueChange = { it == DismissValue.DismissedToStart })
                        val updatedDismissState by rememberUpdatedState(dismissState)

                        SwipeToDismiss(
                            modifier = Modifier.animateItemPlacement(),
                            state = dismissState,
                            background = { SwipeBackGround() },
                            directions = setOf(DismissDirection.EndToStart),
                            dismissContent = {
                                ItemOrder(
                                    imageUrl = orderItem.imageUrl,
                                    orderId = orderItem.orderId,
                                    marketName = orderItem.marketName,
                                    quantity = orderItem.quantity,
                                    price = orderItem.totalPrice,
                                    onClickCard = { onClickItemOrder(orderItem.orderId) }
                                )
                            })
                        LaunchedEffect(showDialog) {
                            if (!showDialog) {
                                dismissState.reset()
                            }
                        }
                        LaunchedEffect(updatedDismissState.dismissDirection) {
                            if (updatedDismissState.dismissDirection == DismissDirection.EndToStart) {
                                showDialog = true
                            }
                        }
                        if (showDialog) {
                            val textOrderStates = when (state.orderStates) {
                                OrderStates.PROCESSING -> stringResource(id = R.string.order_dialog_Cancel_Text)
                                else -> stringResource(id = R.string.order_dialog_Delete_Text)
                            }
                            val buttonOrderStates = when (state.orderStates) {
                                OrderStates.PROCESSING -> OrderStates.CANCELED.state
                                else -> OrderStates.DELETE.state
                            }
                            CustomAlertDialog(
                                message = textOrderStates,
                                onConfirm = {
                                    ordersInteractionsListener.updateOrders(
                                        index.toLong(),
                                        buttonOrderStates
                                    )
                                    showDialog = false
                                },
                                onCancel = { showDialog = false },
                                onDismissRequest = { showDialog = false }
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
fun PreviewOrdersScreen() {
    OrdersScreen()
}