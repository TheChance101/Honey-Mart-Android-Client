package org.the_chance.honeymart.ui.features.orders.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.orders.OrderStates
import org.the_chance.honeymart.ui.features.orders.OrdersInteractionsListener
import org.the_chance.honeymart.ui.features.orders.OrdersUiState
import org.the_chance.honeymart.ui.features.orders.all
import org.the_chance.honeymart.ui.features.orders.cancel
import org.the_chance.honeymart.ui.features.orders.done
import org.the_chance.honeymart.ui.features.orders.emptyOrdersPlaceHolder
import org.the_chance.honeymart.ui.features.orders.pending
import org.the_chance.honeymart.ui.features.orders.processing
import org.the_chance.honymart.ui.theme.background
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun AllOrdersContent(
    state: OrdersUiState,
    listener: OrdersInteractionsListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.dimens.space24)
            .background(background)
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
                state = state.all(),
                text = stringResource(R.string.all),
                onClick = { listener.getAllMarketOrder(OrderStates.ALL) }
            )
            CustomChip(
                state = state.pending(),
                text = stringResource(R.string.new_order),
                onClick = { listener.getAllMarketOrder(OrderStates.PENDING) }
            )
            CustomChip(
                state = state.processing(),
                text = stringResource(id = R.string.processing),
                onClick = { listener.getAllMarketOrder(OrderStates.PROCESSING) }
            )
            CustomChip(
                state = state.done(),
                text = stringResource(id = R.string.done),
                onClick = { listener.getAllMarketOrder(OrderStates.DONE) }
            )
            CustomChip(
                state = state.cancel(),
                text = stringResource(id = R.string.cancel),
                onClick = { listener.getAllMarketOrder(OrderStates.CANCELED) }
            )
        }
        EmptyOrdersPlaceholder(
            painter = painterResource(id = R.drawable.owner_empty_order),
            text = stringResource(R.string.there_are_no_order_for_this_day),
            visibilityState = state.emptyOrdersPlaceHolder(),
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
            contentPadding = PaddingValues(MaterialTheme.dimens.space16)
        ) {
            items(state.orders.size) { index ->
                ItemOrder(
                    onClickCard = {
                        listener.onClickOrder(
                            orderDetails = state.orders[index],
                            id = state.orders[index].orderId
                        )
                    },
                    state = state.orders[index],
                    isSelected = state.orders[index].isOrderSelected,
                )
            }
        }
    }
}