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
fun OrderStatusButton(
    modifier: Modifier=Modifier,
    confirmText: String,
    cancelText: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(modifier = modifier.weight(2F))
        HoneyFilledButton(
            modifier = modifier.width(146.dp),
            label = confirmText,
            onClick = {},
        )
        HoneyFilledButton(
            modifier = modifier.width(146.dp),
            label = cancelText,
            onClick = {},
        )

    }

}