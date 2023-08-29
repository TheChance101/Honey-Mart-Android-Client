package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun HoneyOutlineText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.displaySmall.copy(baselineShift = BaselineShift(0.5f)),
        color = color,
        modifier = modifier
            .wrapContentWidth()
            .height(MaterialTheme.dimens.smallButton)
            .clip(CircleShape)
            .border(
                BorderStroke(1.dp, color),
                shape = CircleShape
            )
            .padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space8
            )
    )
}
