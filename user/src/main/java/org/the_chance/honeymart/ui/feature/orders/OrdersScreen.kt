package org.the_chance.honeymart.ui.feature.orders

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.orders.composable.CustomChip
import org.the_chance.honeymart.ui.feature.orders.composable.OrdersInteractionsListener
import org.the_chance.honeymart.ui.feature.orders.composable.PlaceholderItem
import org.the_chance.honeymart.ui.feature.orders.composable.SwipeBackground
import org.the_chance.honeymart.ui.feature.wishlist.compose.LoadingAnimation
import org.the_chance.honymart.ui.composables.CustomAlertDialog
import org.the_chance.honymart.ui.composables.ItemOrder

@Composable
fun OrdersScreen(
    viewModel: OrderViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    AnimatedVisibility(
        visible = state.isLoading,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoadingAnimation()
        }
    }
    OrdersContent(
        state = state,
        ordersInteractionsListener = viewModel
    )
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun OrdersContent(
    state: OrdersUiState,
    ordersInteractionsListener: OrdersInteractionsListener
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomChip(
                state = state.orderStates == OrderStates.PROCESSING,
                text = stringResource(id = R.string.processing),
                onClick = { ordersInteractionsListener.onClickProcessingOrder() }
            )
            CustomChip(
                state = state.orderStates == OrderStates.DONE,
                text = stringResource(id = R.string.done),
                onClick = { ordersInteractionsListener.onClickDoneOrder() }
            )
            CustomChip(
                state = state.orderStates == OrderStates.CANCELED,
                text = stringResource(id = R.string.cancel),
                onClick = { ordersInteractionsListener.onClickCancelOrder() }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        AnimatedVisibility(
            visible = !state.isLoading,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)) + slideInHorizontally(),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000)) + slideOutHorizontally()
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
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
                        background = { SwipeBackground(dismissState = dismissState) },
                        directions = setOf(DismissDirection.EndToStart),
                        dismissContent = {
                            ItemOrder(
                                imageUrl = orderItem.imageUrl!!,
                                orderId = orderItem.orderId!!,
                                marketName = orderItem.marketName!!,
                                quantity = orderItem.quantity!!,
                                price = orderItem.totalPrice!!,
                                onClick = { ordersInteractionsListener.onClickOrder(orderItem.orderId) }
                            )
                        })
                    LaunchedEffect(showDialog) {
                        if (!showDialog && updatedDismissState.dismissDirection == DismissDirection.EndToStart) {
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
                            else -> {
                                stringResource(id = R.string.order_dialog_Delete_Text)
                            }
                        }

                        val buttonOrderStates = when (state.orderStates) {
                            OrderStates.PROCESSING -> OrderStates.CANCELED.state
                            else -> {
                                OrderStates.DELETE.state
                            }
                        }
                        CustomAlertDialog(
                            message = textOrderStates,
                            onConfirm = {
                                ordersInteractionsListener.onClickConfirmOrder(
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
            if (state.orders.isEmpty()) {
                PlaceholderItem(
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp),
                    image = painterResource(id = R.drawable.placeholder_order),
                    title = stringResource(R.string.placeholder_title),
                    subtitle = stringResource(R.string.placeholder_subtitle),
                    onClickDiscoverMarkets = { ordersInteractionsListener.onClickDiscoverMarkets() }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewOrdersScreen() {
    OrdersScreen()
}