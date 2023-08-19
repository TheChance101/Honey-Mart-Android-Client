package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun HomeMarketItem(
    name: String,
    image: String,
    modifier: Modifier = Modifier,
    onclick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(MaterialTheme.dimens.card)
            .clip(shape = RoundedCornerShape(MaterialTheme.dimens.space12)).clickable { onclick() }
    ) {
        ImageNetwork(
            imageUrl = image,
            contentDescription = "image",
            modifier = Modifier
                .clip(shape = Shapes.medium)
                .background(black16)
                .height(MaterialTheme.dimens.card),
        )
        Text(
            text = name,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Preview
@Composable
fun HomeHorizontalItemsPreview() {
    HoneyMartTheme {
        HomeMarketItem(
            name = "Test",
            image = "Test"
        )
    }
}