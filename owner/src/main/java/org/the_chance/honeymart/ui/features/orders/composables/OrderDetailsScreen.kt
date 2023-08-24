package org.the_chance.honeymart.ui.features.orders.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.orders.OrdersUiState
import org.the_chance.honeymart.ui.features.orders.contentScreen
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrderDetailsContent(
    state: OrdersUiState,
) {
    Loading(state = state.isLoading)
    ContentVisibility(state = state.contentScreen()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    all = MaterialTheme.dimens.space24,
                )
        ) {
            ItemOrder(
                orderId = state.orderDetails.orderId,
                count = state.products.size,
                price = state.orderDetails.totalPrice,
                isSelected = !state.isSelected
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space24)
            ) {

                items(state.products.size) { index ->
                    if (state.products[index].images.isEmpty()) {

                        OrderDetailsCard(
                            onClick = { },
                            imageUrl = "https://lh3.googleusercontent.com/OPo1J6Cvyq28QdAqC5SlW6io6YV9FUCLzGM0OmKbkdZgdMM-ziLJYF96DeJ1YaNi0Kpr9CIqPm8=w128-h128-e365-rj-sc0x00ffffff",
                            productName = state.products[index].name,
                            productPrice = state.products[index].price.toString(),
                            count = state.products[index].count
                        )

                    } else {
                        OrderDetailsCard(
                            onClick = { },
                            imageUrl = state.products[index].images.first(),
                            productName = state.products[index].name,
                            productPrice = state.products[index].price.toString(),
                            count = state.products[index].count
                        )
                    }
                }
            }

            HoneyFilledIconButton(
                label = "Done",
                onClick = {},
                iconPainter = painterResource(id = R.drawable.icon_cart)
            )

        }
    }
}
