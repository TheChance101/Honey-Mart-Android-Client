package org.the_chance.honeymart.ui.feature.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.feature.orders.composable.CustomChip
import org.the_chance.honeymart.ui.feature.orders.composable.SwipeBackground
import org.the_chance.honeymart.ui.feature.uistate.OrdersUiState
import org.the_chance.honymart.ui.composables.ItemOrder

@Composable
fun OrdersScreen(
    viewModel: OrderViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    OrdersContent(state = state)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrdersContent(
    state: OrdersUiState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomChip(state = true, text = "Processing", onClick = { })
            CustomChip(state = false, text = "Done", onClick = { })
            CustomChip(state = false, text = "Cancel", onClick = { })
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(
                items = FakeList.fakeData,
                key = null
            ) { orderItem ->
                val dismissState = rememberDismissState()
                SwipeToDismiss(
                    state = dismissState,
                    background = { SwipeBackground(dismissState = dismissState) },
                    directions = setOf(DismissDirection.EndToStart),
                ) {
                    ItemOrder(
                        imageUrl = orderItem.imageUrl,
                        orderId = orderItem.orderId,
                        marketName = orderItem.marketName,
                        quantity = orderItem.quantity,
                        price = orderItem.orderPrice
                    )
                }
            }
        }
        /*PlaceholderItem(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp),
            image = painterResource(id = R.drawable.no_orders_placeholder),
            title = "You Don't have any orders!!",
            subtitle = "It's a catastrophe! Let's fix it by adding items that catch your eye!",
            onClickDiscoverMarkets = { }
        )*/
    }
}

@Preview
@Composable
fun PreviewOrdersScreen() {
    OrdersScreen()
}