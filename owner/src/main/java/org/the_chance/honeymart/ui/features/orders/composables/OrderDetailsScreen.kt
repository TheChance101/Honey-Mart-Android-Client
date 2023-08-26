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
import org.the_chance.honeymart.ui.features.orders.OrdersInteractionsListener
import org.the_chance.honeymart.ui.features.orders.OrdersUiState
import org.the_chance.honeymart.ui.features.orders.cancel
import org.the_chance.honeymart.ui.features.orders.contentScreen
import org.the_chance.honeymart.ui.features.orders.done
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
                OrderHeader(
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
            OrderStatusButtons(
                visibility = state.products.isNotEmpty() && !state.showState.showProductDetails
                        && !state.done() && !state.cancel(),
                buttonState = state.orderDetails.buttonsState
            )
        }
    }
}

