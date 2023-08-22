package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.blackOn87
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white

@Composable
fun ProductItem(
    productName: String,
    productPrice: String,
    imageUrl: String,
    isFavoriteIconClicked: Boolean,
    onClick: () -> Unit,
    onClickFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
) {
    Box(
        modifier = modifier
            .size(
                width = MaterialTheme.dimens.heightItemMarketCard,
                height = MaterialTheme.dimens.widthItemMarketCard
            )
            .clip(RoundedCornerShape(MaterialTheme.dimens.space16))
            .clickable { onClick() }
    )
    {
        ImageNetwork(
            imageUrl = imageUrl,
            contentDescription = stringResource(R.string.product_item),
            contentScale = ContentScale.Crop,
        )

        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    end = MaterialTheme.dimens.space8,
                    top = MaterialTheme.dimens.space8
                ),
            backgroundColor = if (isFavoriteIconClicked) MaterialTheme.colorScheme.tertiary
            else MaterialTheme.colorScheme.primary,
            onClick = { if (enable) onClickFavorite() }
        ) {
            Icon(
                painter = painterResource(
                    id = if (isFavoriteIconClicked) R.drawable.icon_favorite_selected
                    else R.drawable.icon_favorite_unselected
                ),
                tint = if (isFavoriteIconClicked) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.tertiary,
                contentDescription = stringResource(R.string.favorite_icon),
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            blackOn87,
                        ),
                        startY = 50f,
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = MaterialTheme.dimens.space8,
                    bottom = MaterialTheme.dimens.space8
                ),
        ) {
            Text(
                text = productName,
                style = MaterialTheme.typography.displayLarge.copy(white)
            )
            Text(
                text = productPrice,
                style = MaterialTheme.typography.displayLarge.copy(white)
            )
        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    HoneyMartTheme {
        ProductItem(
            isFavoriteIconClicked = false,
            onClickFavorite = {},
            productName = "Product Name",
            productPrice = "Rp 100.000",
            imageUrl = "https://picsum.photos/200/300",
            onClick = {}
        )
    }
}