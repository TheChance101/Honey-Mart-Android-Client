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
import org.the_chance.honymart.ui.composables.HoneyOutlineButton

@Composable
fun OrderStatusButton(
    modifier: Modifier = Modifier,
    modifierFillButton: Modifier = Modifier,
    modifierOnlineButton: Modifier = Modifier,
    confirmText: String,
    cancelText: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(modifier = Modifier.weight(1F))
        HoneyFilledButton(
            modifier = modifierFillButton.width(165.dp),
            label = confirmText,
            onClick = {},
        )
        HoneyOutlineButton(
            modifier = modifierOnlineButton.width(165.dp),
            label = cancelText,
            onClick = {},
        )

    }

}