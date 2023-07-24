package org.the_chance.honymart.ui.screens.composable

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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun CustomSmallIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes idIconDrawableRes: Int,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    background: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = CircleShape,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .clip(shape)
            .size(MaterialTheme.dimens.smallButton)
            .background(background),

        ) {

        Image(
            painter = painterResource(id = idIconDrawableRes),
            colorFilter = ColorFilter.tint(color = contentColor),
            contentDescription = "",
            modifier = Modifier
                .size(MaterialTheme.dimens.iconMedium),
        )


    }
}

@Preview
@Composable
private fun CustomButtonPreview() {
    HoneyMartTheme {
        CustomSmallIconButton(
            idIconDrawableRes = R.drawable.icon_favorite_selected
        )
        {}
    }
}