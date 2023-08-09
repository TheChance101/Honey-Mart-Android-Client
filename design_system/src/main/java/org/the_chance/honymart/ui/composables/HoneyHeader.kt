package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.Typography

/**
 * Created by Aziza Helmy on 8/9/2023.
 */
@Composable
fun HoneyHeader(title: String, subTitle: String, textColor: Color, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = Typography.headlineMedium.copy(color = textColor),
        textAlign = TextAlign.Center,
        modifier = modifier

    )
    Text(
        text = subTitle,
        style = Typography.bodyMedium.copy(color = textColor),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 100.dp)
    )
}
