package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.modifier.brushBottomToTop
import org.the_chance.honymart.ui.screens.composable.CustomSmallIconButton
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ItemFavorite(
    modifier: Modifier = Modifier,
    imageUrlMarket: String,
    name: String,
    price: String,
    description: String,
    onClick: () -> Unit,
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.heightItemFavorite)
            .clickable(onClick = onClick), shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            ImageNetwork(
                imageUrlMarket, modifier = Modifier.brushBottomToTop()
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.space8)

            ) {

                CustomSmallIconButton(
                    modifier = modifier.align(Alignment.End),
                    idIconDrawableRes = R.drawable.icon_favorite_selected,
                    background = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {}
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    name,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    price,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    description,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

        }
    }
}

@Preview
@Composable
private fun ItemFavoritePreview() {
    HoneyMartTheme {
        ItemFavorite(
            imageUrlMarket = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg",
            name = "Sofa",
            price = "30,000\$",
            description = "Secondary text",
        ) {}
    }
}

