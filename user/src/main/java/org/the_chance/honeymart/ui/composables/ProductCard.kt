package org.the_chance.honeymart.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.blackOn87
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    productName: String,
    productPrice: String,
    secondaryText: String,
    isFavoriteIconClicked: Boolean,
    onClickFavorite: () -> Unit,
    enable: Boolean,
    onClickCard: () -> Unit,
    visibility: Boolean = true,
    imageUrl: String,
) {
    Box(
        modifier = modifier
            .height(height = 200.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable { if (enable) onClickCard() },

    ) {
        ImageNetwork(
            modifier = Modifier.fillMaxSize(),
            imageUrl = imageUrl
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
            onClick = { if(enable)onClickFavorite() }
        ) {
            Image(
                painter = painterResource(
                    id = if (isFavoriteIconClicked) R.drawable.icon_favorite_selected
                    else R.drawable.icon_favorite_unselected
                ),
                contentDescription = stringResource(R.string.favorite_icon),
            )
            ImageNetwork(
                modifier = Modifier.fillMaxSize(),
                imageUrl = imageUrl
            )
        ContentVisibility(state = visibility) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        end = MaterialTheme.dimens.space8,
                        top = MaterialTheme.dimens.space8
                    ),
                backgroundColor = if (isFavoriteIconClicked) MaterialTheme.colorScheme.tertiary
                else MaterialTheme.colorScheme.primary,
                onClick = onClickFavorite,
                size = MaterialTheme.dimens.icon32
            ) {
                Image(
                    painter = painterResource(
                        id = if (isFavoriteIconClicked) R.drawable.icon_favorite_selected
                        else R.drawable.icon_favorite_unselected
                    ),
                    contentDescription = stringResource(R.string.favorite_icon),
                )
            }
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
                        startY = 300f,
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = MaterialTheme.dimens.space8,
                    bottom = MaterialTheme.dimens.space8,
                    end = MaterialTheme.dimens.space8
                )
        ) {
            Text(
                text = productName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$productPrice$",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = secondaryText,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = MaterialTheme.dimens.space8,
                        bottom = MaterialTheme.dimens.space8,
                        end = MaterialTheme.dimens.space8
                    )
            ) {
                Text(
                    text = productName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$productPrice$",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = secondaryText,
                    style = Typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

    }
}

@Preview
@Composable
fun ProductCardPreview() {
    ProductCard(
        imageUrl = "https://img.freepik.com/free-photo/mid-century-modern-living-room-interior-design-with-monstera-tree_53876-129804.jpg",
        productName = "To Kill a Mockingbird",
        productPrice = "30,000",
        secondaryText = "Secondary Text",
        isFavoriteIconClicked = true,
        enable = true,
        onClickFavorite = {},
        onClickCard = {}
    )
}