package org.the_chance.honeymart.ui.features.orders.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.orders.OrdersUiState
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyOutlineButton

@Composable
fun OrderStatusButton(
    confirmText: String,
    cancelText: String,
    state: OrdersUiState,
    onClickConfirm: () -> Unit,
    onClickCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    ContentVisibility(state = state.products.isNotEmpty() && !state.showState.showProductDetails) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Spacer(modifier = Modifier.weight(1F))
            HoneyFilledButton(
                modifier = modifier.width(165.dp),
                label = confirmText,
                onClick = onClickConfirm,
            )
            HoneyOutlineButton(
                modifier = modifier
                    .width(165.dp)
                    .padding(bottom = 24.dp),
                label = cancelText,
                onClick = onClickCancel,
            )
        }
    }
}