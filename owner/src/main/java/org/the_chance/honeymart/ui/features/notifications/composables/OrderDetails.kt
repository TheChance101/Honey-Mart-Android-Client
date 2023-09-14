package org.the_chance.honeymart.ui.features.notifications.composables

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
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.notifications.NotificationsUiState
import org.the_chance.honeymart.ui.features.orders.composables.OrderDetailsCard
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrderDetails(
    state: NotificationsUiState,
) {
        Scaffold(
            topBar = {
                OrdersHeader(
                    state = state,
                    isSelected = !state.notification.isNotificationSelected,
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
                                onClick = { },
                                state = product,
                            )
                        }
                    }
                }
            }
        }
    }


@Preview
@Composable
fun PreviewOrderDetails() {
    OrderDetails(state = NotificationsUiState())
    
}