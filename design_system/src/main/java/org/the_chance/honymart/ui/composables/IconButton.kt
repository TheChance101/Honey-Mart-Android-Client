package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import org.the_chance.design_system.R

@Composable
fun IconButton(
    size: Dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable () -> Unit,
) {
    IconButton(
        modifier = modifier
            .size(size)
            .background(backgroundColor, shape = shape)
            .clip(shape),
        onClick = onClick
    ) {
        content()
    }
}

@Preview
@Composable
fun IconButtonPreview() {
    IconButton(
        content = {
            Icon(
                painter = painterResource(id = R.drawable.icon_favorite_unselected),
                contentDescription = "",
                tint = Color.White
            )
        },
        onClick = {}
    )
}