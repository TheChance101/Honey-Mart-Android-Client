package org.the_chance.honeymart.ui.features.orders.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.composables.HoneyFilledButton

@Composable
fun OrderStatus() {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        Spacer(modifier = Modifier.weight(1F))
        HoneyFilledButton(
            modifier = Modifier.width(146.dp),
            label = "Approve",
            onClick = {},
        )
        HoneyFilledButton(
            modifier = Modifier.width(146.dp),
            label = "Declined",
            onClick = {},
        )
    }
}