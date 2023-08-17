package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white

@Preview
@Composable
fun NewProductsItems(
    modifier: Modifier = Modifier,
    isFavoriteIconClicked: Boolean = false,
    onClickFavorite: () -> Unit = {},
    enable: Boolean = true

) {
    Box(
        modifier = modifier
            .size(width = 160.dp, height = 136.dp)
            .clip(RoundedCornerShape(16.dp))
            .padding(
                end = MaterialTheme.dimens.space8,
                top = MaterialTheme.dimens.space8
            ),
    )
    {
        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd),
            backgroundColor = if (isFavoriteIconClicked) MaterialTheme.colorScheme.tertiary
            else MaterialTheme.colorScheme.primary,
            onClick = { if (enable) onClickFavorite() }
        ) {
            Icon(
                painter = painterResource(
                    id = if (isFavoriteIconClicked) R.drawable.icon_favorite_selected
                    else R.drawable.icon_favorite_unselected
                ),
                contentDescription = stringResource(R.string.favorite_icon),
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = MaterialTheme.dimens.space8,
                    bottom = MaterialTheme.dimens.space8
                )

        ) {
            Text(
                text = "blah blah blah",
                style = Typography.displayLarge.copy(white)
            )
            Text(
                text = "30000$",
                style = Typography.displayLarge.copy(white)
            )

        }
    }
}