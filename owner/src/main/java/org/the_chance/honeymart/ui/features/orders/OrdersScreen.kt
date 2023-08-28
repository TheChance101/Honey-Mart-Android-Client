package org.the_chance.honeymart.ui.features.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.features.orders.composables.AllOrdersContent
import org.the_chance.honeymart.ui.features.orders.composables.OrderDetailsContent
import org.the_chance.honeymart.ui.features.orders.composables.ProductDetailsInOrderContent
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.Loading

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        HoneyMartTitle()
        ConnectionErrorPlaceholder(
            state = state.errorPlaceHolderCondition(),
            onClickTryAgain = { listener.getAllMarketOrder(OrderStates.ALL) }
        )
        Loading(state = state.isLoading && state.orders.isEmpty())
        Row(modifier = Modifier.fillMaxSize()) {
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
                    state = state.orders.isNotEmpty()
                            && state.products.isNotEmpty()
                            && !state.showState.showProductDetails
                            && state.showState.showOrderDetails
                ) {
                    OrderDetailsContent(
                        state = state,
                        listener = listener
                    )
                }
                ContentVisibility(state = state.showState.showProductDetails &&
                        !state.showState.showOrderDetails) {
                    ProductDetailsInOrderContent(
                        titleScreen =
                        stringResource(id = R.string.product_details), state = state
                    )

                }
            }
        }
    }
}