package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun HoneyFavIconButton(
    onClick: () -> Unit,
    iconPainter: Painter,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    background: Color = MaterialTheme.colorScheme.primary,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .clip(shape)
            .size(MaterialTheme.dimens.smallButton)
            .background(background),

        ) {

        Image(
            painter = iconPainter,
            contentDescription = "",
            modifier = Modifier.size(MaterialTheme.dimens.icon24),
        )


    }
}

@Preview
@Composable
private fun HoneyMartSmallIconPreview() {
    HoneyFavIconButton(
        iconPainter = painterResource(id = R.drawable.icon_favorite_selected),
        onClick = {}
    )

}
