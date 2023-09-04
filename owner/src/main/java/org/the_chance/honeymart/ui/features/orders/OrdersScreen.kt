package org.the_chance.honeymart.ui.features.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.components.Placeholder
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.features.orders.composables.AllOrdersContent
import org.the_chance.honeymart.ui.features.orders.composables.OrderDetailsContent
import org.the_chance.honeymart.ui.features.orders.composables.OrderPlaceHolder
import org.the_chance.honeymart.ui.features.orders.composables.ProductDetailsInOrderContent
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(lifecycleOwner) {
        viewModel.resetStateScreen()
        viewModel.getAllMarketOrder(OrderStates.ALL)
    }

    OrdersContent(state, viewModel)
}

@Composable
fun OrdersContent(
    state: OrdersUiState,
    listener: OrdersInteractionsListener,
) {
    ConnectionErrorPlaceholder(
        state = state.errorPlaceHolderCondition(),
        onClickTryAgain = { listener.getAllMarketOrder(OrderStates.ALL) }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HoneyMartTitle()
        Loading(
            state = state.loadingScreen())
        ContentVisibility(state = state.emptyPlaceHolder()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                OrderPlaceHolder(painter = painterResource(id = R.drawable.owner_empty_order),
                    text = stringResource(R.string.there_are_no_orders_in_your_market),
                    onClick = { listener.getAllMarketOrder(OrderStates.ALL) }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.dimens.space16),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                ContentVisibility(state = state.showOrdersState())
                {
                    AllOrdersContent(state = state, listener = listener)
                }
                ContentVisibility(state = state.showOrderDetails()) {
                    OrderDetailsContent(
                        state = state,
                        listener = listener
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                ContentVisibility(
                    state = state.showOrderDetailsInRight()) {
                    OrderDetailsContent(
                        state = state,
                        listener = listener
                    )
                }
                ContentVisibility(
                    state = state.showState.showProductDetails &&
                            !state.showState.showOrderDetails
                ) {
                    ProductDetailsInOrderContent(
                        titleScreen =
                        stringResource(id = R.string.product_details), state = state
                    )
                }
                ContentVisibility(state = state.showClickOrderPlaceHolder()) {
                    Placeholder(
                        painter = painterResource(id = R.drawable.owner_empty_order),
                        text = stringResource(R.string.click_on_order_to_show_it_s_details),
                    )
                }
                Loading(state = state.isLoading && state.showState.showOrderDetails)

            }
        }
    }

}