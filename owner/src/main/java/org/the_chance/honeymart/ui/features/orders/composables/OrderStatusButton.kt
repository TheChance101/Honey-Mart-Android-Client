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
import org.the_chance.honeymart.ui.features.category.Visibility
import org.the_chance.honeymart.ui.features.orders.ButtonsState
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyOutlineButton

@Composable
fun OrderStatusButtons(
    visibility: Boolean,
    buttonState: ButtonsState,
    modifier: Modifier = Modifier
) {
    ContentVisibility(visibility) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Spacer(modifier = Modifier.weight(1F))
            HoneyFilledButton(
                modifier = modifier.width(165.dp),
                label = buttonState.confirmText,
                onClick = buttonState.onClickConfirm,
            )
            HoneyOutlineButton(
                modifier = modifier
                    .width(165.dp)
                    .padding(bottom = 24.dp),
                label = buttonState.cancelText,
                onClick = buttonState.onClickCancel,
            )
        }
    }
}