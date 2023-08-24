package org.the_chance.honeymart.ui.feature.notifications.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun StateItem(
    painter: Painter,
    color: Color,
    text: String,
    onClickState: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space4),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = modifier
                .border(
                    width = MaterialTheme.dimens.space1,
                    color = color,
                    shape = CircleShape
                ),
            size = MaterialTheme.dimens.space40,
            onClick = onClickState,
            backgroundColor = Color.Transparent
        ) {
            Icon(
                painter = painter,
                contentDescription = "",
                tint = color,
                modifier = Modifier.size(MaterialTheme.dimens.space20)
            )
        }
        Text(
            text = text,
            color = color,
            textAlign = TextAlign.Center,
            style = Typography.displaySmall
        )
    }
}