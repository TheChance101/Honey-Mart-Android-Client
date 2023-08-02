package org.the_chance.honymart.ui.composables

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun CustomSmallIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes idIconDrawableRes: Int,
    background: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = CircleShape,
    iconSize: Dp = MaterialTheme.dimens.iconMedium,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier
            .clip(shape)
            .size(MaterialTheme.dimens.smallButton)
            .background(background),
        onClick = onClick,
        ) {

        Image(
            painter = painterResource(id = idIconDrawableRes),
            contentDescription = "",
            modifier = Modifier.size(iconSize),
        )


    }
}

@Preview
@Composable
private fun CustomSmallIconPreview() {
    HoneyMartTheme {
        CustomSmallIconButton(
            idIconDrawableRes = R.drawable.icon_favorite_selected
        )
        {}
    }
}