package org.the_chance.honeymart.ui.feature.orders.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun CustomChip(
    state: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        modifier = modifier.clickable { onClick() },
        colors = if (state) ButtonDefaults.buttonColors(primary100)
        else ButtonDefaults.buttonColors(Transparent),
        border = if (state) BorderStroke(width = 0.dp, color = Transparent)
        else BorderStroke(width = 1.dp, color = primary100),
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = if (state) Color.White else primary100,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        )
    }
}

@Preview
@Composable
fun PreviewCustomChip() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomChip(state = true, text = "Processing", onClick = { })
        CustomChip(state = false, text = "Done", onClick = { })
        CustomChip(state = false, text = "Cancel", onClick = { })
    }
}