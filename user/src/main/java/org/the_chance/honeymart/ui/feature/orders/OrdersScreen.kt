package org.the_chance.honeymart.ui.feature.orders

import SwipeBackground
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
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honeymart.ui.composables.ItemOrder
import org.the_chance.honeymart.ui.feature.market.navigateToMarketScreen
import org.the_chance.honeymart.ui.feature.order_details.navigateToOrderDetailsScreen
import org.the_chance.honeymart.util.collect
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.CustomAlertDialog
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrdersScreen(
    viewModel: OrderViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current
    val lifecycleOwner = LocalLifecycleOwner.current

    lifecycleOwner.collect(viewModel.effect) { effect ->
        effect.getContentIfHandled()?.let {
            when (it) {
                OrderUiEffect.ClickDiscoverMarketsEffect -> navController.navigateToMarketScreen()
                is OrderUiEffect.ClickOrderEffect -> navController.navigateToOrderDetailsScreen(it.orderId)
            }
        }
    }
    LaunchedEffect(lifecycleOwner) {
        viewModel.getAllProcessingOrders()
    }
    OrdersContent(
        state = state,
        listener = viewModel,

        )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OrdersContent(
    state: OrdersUiState,
    listener: OrdersInteractionsListener,
) {
    AppBarScaffold {
        ConnectionErrorPlaceholder(
            state = state.isError,
            onClickTryAgain = listener::getAllProcessingOrders
        )
        EmptyOrdersPlaceholder(
            state = state.emptyOrdersPlaceHolder(),
            image = R.drawable.placeholder_order,
            title = stringResource(R.string.placeholder_title),
            subtitle = stringResource(R.string.placeholder_subtitle),
            onClickDiscoverMarkets = listener::onClickDiscoverMarkets
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
                    state = state.processing(),
                    text = stringResource(id = R.string.processing),
                    onClick = listener::getAllProcessingOrders
                )
                CustomChip(
                    state = state.done(),
                    text = stringResource(id = R.string.done),
                    onClick = listener::getAllDoneOrders
                )
                CustomChip(
                    state = state.cancel(),
                    text = stringResource(id = R.string.cancel),
                    onClick = listener::getAllCancelOrders
                )
            }

            Loading(state = state.isLoading)

            ContentVisibility(state = state.screenContent()) {
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
                            background = { SwipeBackground() },
                            directions = setOf(DismissDirection.EndToStart),
                            dismissContent = {
                                ItemOrder(
                                    imageUrl = orderItem.imageUrl,
                                    orderId = orderItem.orderId,
                                    marketName = orderItem.marketName,
                                    quantity = orderItem.quantity,
                                    price = orderItem.totalPrice,
                                    onClickCard = listener::onClickOrder,
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
                                    listener.updateOrders(
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