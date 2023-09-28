package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.honymart.ui.theme.Typography

@Composable
fun HoneyFilledText(
    text: String,
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = MaterialTheme.shapes.medium,
    textColor: Color = Color.White,
    textStyle: TextStyle = Typography.bodySmall,
    ) {
        Row(
            modifier = Modifier.background(background, shape),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = modifier,
                text = text,
                color = textColor,
                style = textStyle
            )
        }
}

@Preview
@Composable
fun PriceLabelPreview() {
    HoneyFilledText(text = "30,000$")
}