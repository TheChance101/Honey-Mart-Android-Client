package org.the_chance.honeymart.ui.features.orders.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.orders.OrderStates
import org.the_chance.honeymart.ui.features.orders.OrdersInteractionsListener
import org.the_chance.honeymart.ui.features.orders.OrdersUiState
import org.the_chance.honeymart.ui.features.orders.contentScreen
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrderDetailsContent(
    state: OrdersUiState,
    listener: OrdersInteractionsListener
) {
    Loading(state = state.isLoading)
    ContentVisibility(state = state.contentScreen()) {
        Box(contentAlignment = Alignment.BottomCenter) {
            Column(
                modifier = Modifier
                    .background(color = Color.White, shape = MaterialTheme.shapes.medium)
                    .fillMaxSize()
                    .padding(all = MaterialTheme.dimens.space24)
            ) {
                ItemOrder(
                    orderId = state.orderId,
                    count = state.products.size,
                    state = state.orderDetails,
                    isSelected = !state.orderDetails.isSelected
                )
                ContentVisibility(state = state.products.isNotEmpty() && !state.isLoading) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                        contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space24)
                    ) {
                        items(state.products.size) { index ->
                            OrderDetailsCard(
                                onClick = { listener.onClickProduct(state.products[index]) },
                                state = state.products[index]
                            )
                        }
                    }
                }
            }

            when (state.order.states) {
                OrderStates.ALL -> OrderStatusButton(
                    confirmText = "Approved",
                    cancelText = "Cancel",
                    state = state,
                    onClickConfirm = {
                        listener.updateStateOrder(
                            id = state.orderId,
                            updateState = OrderStates.PROCESSING
                        )
                    },
                    onClickCancel = {
                        listener.updateStateOrder(
                            state.orderId,
                            updateState = OrderStates.CANCELED
                        )
                    },
                )

                OrderStates.PENDING -> OrderStatusButton(
                    confirmText = "Pending",
                    cancelText = "Cancel",
                    state = state,
                    onClickConfirm = {
                        listener.updateStateOrder(
                            id = state.orderId,
                            updateState = OrderStates.PROCESSING
                        )
                    },
                    onClickCancel = {
                        listener.updateStateOrder(
                            state.orderId,
                            updateState = OrderStates.CANCELED
                        )
                    },
                )

                OrderStates.PROCESSING -> OrderStatusButton(
                    confirmText = "Approved",
                    cancelText = "Declined",
                    state = state,
                    onClickConfirm = {
                        listener.updateStateOrder(
                            id = state.orderId,
                            updateState = OrderStates.DONE
                        )
                    },
                    onClickCancel = {
                        listener.updateStateOrder(
                            state.orderId,
                            updateState = OrderStates.CANCELED
                        )
                    },
                )

                OrderStates.DONE -> OrderStatusButton(
                    confirmText = "Approved",
                    cancelText = "Declined",
                    state = state,
                    onClickConfirm = {
                        listener.updateStateOrder(
                            id = state.orderId,
                            updateState = OrderStates.ALL
                        )
                    },
                    onClickCancel = {
                        listener.updateStateOrder(
                            state.orderId,
                            updateState = OrderStates.CANCELED
                        )
                    },
                )

                OrderStates.CANCELED -> OrderStatusButton(
                    onClickConfirm = {
                        listener.updateStateOrder(
                            id = state.orderId,
                            updateState = OrderStates.PROCESSING
                        )
                    },
                    onClickCancel = {
                        listener.updateStateOrder(
                            state.orderId,
                            updateState = OrderStates.CANCELED
                        )
                    },
                    confirmText = "Approved",
                    cancelText = "Declined",
                    state = state,
                )
            }
        }
    }
}

