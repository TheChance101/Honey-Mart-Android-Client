package org.the_chance.honeymart.ui.features.orders

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.orders.composables.CustomChip
import org.the_chance.honeymart.ui.features.orders.composables.EmptyOrdersPlaceholder
import org.the_chance.honeymart.ui.features.orders.composables.ItemOrder
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.background
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    OrdersContent(state, viewModel)
}

@Composable
fun OrdersContent(
    state: OrdersUiState,
    listener: OrdersInteractionsListener,
) {
    Loading(state = state.isLoading)
    EmptyOrdersPlaceholder(
        painter = painterResource(id = R.drawable.owner_empty_order),
        text = stringResource(R.string.there_are_no_order_for_this_day),
        visibilityState = state.emptyOrdersPlaceHolder(),

        )
    AnimatedVisibility(visible = state.screenContent()) {
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
                    onClick = listener::getAllOrders
                )
                CustomChip(
                    state = state.pending(),
                    text = stringResource(R.string.new_order),
                    onClick = listener::getAllPendingOrders
                )
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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                contentPadding = PaddingValues(MaterialTheme.dimens.space16)
            ) {
                items(5) {
                    ItemOrder(
                        onClickCard = listener::onClickOrder,
                        orderId = state.orders[it].orderId,
                        userName = state.orders[it].userName,
                        price = state.orders[it].totalPrice,
                        time = state.orders[it].time
                    )
                }
            }
        }
    }
}