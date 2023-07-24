package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.white

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = CircleShape,
    iconColor: Color = white,
    content: @Composable () -> Unit,
) {
    IconButton(
        modifier = modifier.background(backgroundColor, shape = shape),
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
        }
    )
}