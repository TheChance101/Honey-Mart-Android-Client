package org.the_chance.honeymart.ui.features.orders.composables

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
import org.the_chance.honeymart.ui.features.orders.OrderStates
import org.the_chance.honeymart.ui.features.orders.OrdersInteractionsListener
import org.the_chance.honeymart.ui.features.orders.OrdersUiState
import org.the_chance.honeymart.ui.features.orders.all
import org.the_chance.honeymart.ui.features.orders.cancelByOwner
import org.the_chance.honeymart.ui.features.orders.cancelByUser
import org.the_chance.honeymart.ui.features.orders.done
import org.the_chance.honeymart.ui.features.orders.emptyOrdersPlaceHolder
import org.the_chance.honeymart.ui.features.orders.pending
import org.the_chance.honeymart.ui.features.orders.processing
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun AllOrdersContent(
    state: OrdersUiState,
    listener: OrdersInteractionsListener,
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
                    onClick = { listener.getAllMarketOrders(OrderStates.ALL) }
                )
            }
            item {
                CustomChip(
                    state = state.pending(),
                    text = stringResource(R.string.new_order),
                    onClick = { listener.getAllMarketOrders(OrderStates.PENDING) }
                )
            }
            item {
                CustomChip(
                    state = state.processing(),
                    text = stringResource(id = R.string.processing),
                    onClick = { listener.getAllMarketOrders(OrderStates.PROCESSING) }
                )
            }
            item {
                CustomChip(
                    state = state.done(),
                    text = stringResource(id = R.string.done),
                    onClick = { listener.getAllMarketOrders(OrderStates.DONE) }
                )
            }
            item {
                CustomChip(
                    state = state.cancelByOwner(),
                    text = stringResource(id = R.string.declined),
                    onClick = { listener.getAllMarketOrders(OrderStates.CANCELLED_BY_OWNER) }
                )
            }
            item {
                CustomChip(
                    state = state.cancelByUser(),
                    text = stringResource(id = R.string.cancelled),
                    onClick = { listener.getAllMarketOrders(OrderStates.CANCELLED_BY_USER) }
                )
            }
        }
        ContentVisibility(state = state.emptyOrdersPlaceHolder()) {
            Placeholder(
                painter = painterResource(id = R.drawable.owner_empty_order),
                text = stringResource(R.string.there_are_no_order_for_this_day),
            )
        }
        Loading(
            state = state.isLoadingOrders && !state.emptyOrdersPlaceHolder()
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
            contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space16)
        ) {
            items(
                items = state.orders,
                key = { it.orderId }
            ) { order ->
                ItemOrder(
                    onClickCard = {
                        listener.onClickOrder(
                            orderDetails = order,
                            id = order.orderId
                        )
                    },
                    state = order,
                    isSelected = order.isOrderSelected,
                )
            }
        }
    }
}