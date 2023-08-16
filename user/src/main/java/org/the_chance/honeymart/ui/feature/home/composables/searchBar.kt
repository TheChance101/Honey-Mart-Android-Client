package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.black87

@Composable
fun searchBar(
    modifier: Modifier = Modifier,
    icon: Painter,
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent,
        shape = MaterialTheme.shapes.small.copy(CornerSize(16.dp)),
        border = BorderStroke(1.dp, black16)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Search",
                style = Typography.displaySmall.copy(black87),
            )
        }
    }
}
