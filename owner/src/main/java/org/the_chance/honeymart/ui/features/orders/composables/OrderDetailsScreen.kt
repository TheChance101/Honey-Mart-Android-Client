package org.the_chance.honeymart.ui.features.orders.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.orders.OrdersInteractionsListener
import org.the_chance.honeymart.ui.features.orders.OrdersUiState
import org.the_chance.honeymart.ui.features.orders.all
import org.the_chance.honeymart.ui.features.orders.cancel
import org.the_chance.honeymart.ui.features.orders.contentScreen
import org.the_chance.honeymart.ui.features.orders.done
import org.the_chance.honymart.ui.theme.dimens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderDetailsContent(
    state: OrdersUiState,
    listener: OrdersInteractionsListener
) {
    ContentVisibility(state = state.contentScreen()) {
        Scaffold(
            topBar = {
                OrderHeader(
                    state = state,
                    isSelected = !state.orderDetails.isSelected,
                )
            },
            bottomBar = {
                OrderStatusButtons(
                    visibility = state.products.isNotEmpty() && !state.showState.showProductDetails
                            && !state.done() && !state.cancel() && !state.all(),
                    buttonState = state.orderDetails.buttonsState
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onTertiary,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(paddingValues)
                    .fillMaxHeight()
            ) {
                ContentVisibility(state = state.products.isNotEmpty() && !state.isLoading) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                        contentPadding = PaddingValues(
                            vertical = MaterialTheme.dimens.space24,
                        )
                    ) {
                        items(items = state.products, key = { it.id }) { product ->
                            OrderDetailsCard(
                                onClick = { listener.onClickProduct(product) },
                                state = product,
                            )
                        }
                    }
                }
            }
        }
    }
}

