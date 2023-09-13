package org.the_chance.honeymart.ui.features.orders.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.orders.ButtonsState
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyOutlineButton
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrderStatusButtons(
    visibility: Boolean,
    buttonState: ButtonsState,
    modifier: Modifier = Modifier
) {
    ContentVisibility(visibility) {
        Surface(
            color = MaterialTheme.colorScheme.onTertiary,
            shadowElevation = MaterialTheme.dimens.space8,
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.dimens.space16),
                horizontalArrangement = Arrangement.End,
            ) {
                HoneyFilledButton(
                    modifier = modifier.width(MaterialTheme.dimens.withOrderStatusButton),
                    label = buttonState.confirmText,
                    onClick = buttonState.onClickConfirm,
                )

                HoneyOutlineButton(
                    modifier = modifier.width(MaterialTheme.dimens.withOrderStatusButton),
                    label = buttonState.cancelText,
                    onClick = buttonState.onClickCancel,
                )
            }
        }
    }
}