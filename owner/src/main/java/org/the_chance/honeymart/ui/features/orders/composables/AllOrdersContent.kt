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
import org.the_chance.honeymart.ui.components.CustomChip
import org.the_chance.honeymart.ui.components.Placeholder
import org.the_chance.honeymart.ui.features.orders.OrderStates
import org.the_chance.honeymart.ui.features.orders.OrdersInteractionsListener
import org.the_chance.honeymart.ui.features.orders.OrdersUiState
import org.the_chance.honeymart.ui.features.orders.all
import org.the_chance.honeymart.ui.features.orders.cancel
import org.the_chance.honeymart.ui.features.orders.done
import org.the_chance.honeymart.ui.features.orders.emptyOrdersPlaceHolder
import org.the_chance.honeymart.ui.features.orders.pending
import org.the_chance.honeymart.ui.features.orders.processing
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
                    onClick = { listener.getAllMarketOrder(OrderStates.ALL) }
                )
            }
            item {
                CustomChip(
                    state = state.pending(),
                    text = stringResource(R.string.new_order),
                    onClick = { listener.getAllMarketOrder(OrderStates.PENDING) }
                )
            }
            item {
                CustomChip(
                    state = state.processing(),
                    text = stringResource(id = R.string.processing),
                    onClick = { listener.getAllMarketOrder(OrderStates.PROCESSING) }
                )
            }
            item {
                CustomChip(
                    state = state.done(),
                    text = stringResource(id = R.string.done),
                    onClick = { listener.getAllMarketOrder(OrderStates.DONE) }
                )
            }
            item {
                CustomChip(
                    state = state.cancel(),
                    text = stringResource(id = R.string.cancel),
                    onClick = { listener.getAllMarketOrder(OrderStates.CANCELED) }
                )
            }
        }
        Placeholder(
            painter = painterResource(id = R.drawable.owner_empty_order),
            text = stringResource(R.string.there_are_no_order_for_this_day),
            visibilityState = state.emptyOrdersPlaceHolder(),
        )
        Loading(state = state.isLoading && !state.showState.showOrderDetails)
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