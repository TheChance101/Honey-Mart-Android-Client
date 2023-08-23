package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@Composable
fun CustomChip(
    modifier: Modifier = Modifier,
    state: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            onClick()
        },
        colors = if (state) CardDefaults.cardColors(primary100)
        else CardDefaults.cardColors(Transparent),
        border = if (state) BorderStroke(width = 0.dp, color = Transparent)
        else BorderStroke(width = 1.dp, color = primary100),
        shape = CircleShape
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16, vertical = MaterialTheme.dimens.space6
            ),
            text = text,
            color = if (state) white else primary100,
            style = MaterialTheme.typography.displaySmall.copy(baselineShift = BaselineShift(0.3f))
        )
    }
}

@Preview
@Composable
fun PreviewCustomChip() {
    HoneyMartTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomChip(state = true, text = "Processing", onClick = { })
            CustomChip(state = false, text = "Done", onClick = { })
            CustomChip(state = false, text = "Cancel", onClick = { })
        }
    }
}