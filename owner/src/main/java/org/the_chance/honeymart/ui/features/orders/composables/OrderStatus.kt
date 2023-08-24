package org.the_chance.honeymart.ui.features.orders.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.composables.HoneyFilledButton

@Composable
fun OrderStatus() {
    Row {
        HoneyFilledButton(
            modifier = Modifier.width(146.dp),
            label = "approve",
            onClick = {},
        )
        HoneyFilledButton(
            modifier = Modifier.width(146.dp),
            label = "declined",
            onClick = {},
        )
    }
}