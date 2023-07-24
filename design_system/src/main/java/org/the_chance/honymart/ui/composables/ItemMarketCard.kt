package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.honymart.ui.modifier.fullOverlay
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ItemMarketCard(
    modifier: Modifier = Modifier,
    imageUrlMarket: String,
    nameMarket: String,
    onClick: () -> Unit,
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.heightItemMarketCard)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ImageNetwork(
                imageUrlMarket,
                modifier = Modifier.fullOverlay()
            )
            Text(
                nameMarket,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun ItemMarketCardPreview() {
    HoneyMartTheme {
        ItemMarketCard(
            imageUrlMarket = "https://upload.wikimedia.org/wikipedia/commons/1/13/Supermarkt.jpg",
            nameMarket = "Fresh Fare"
        ) {}
    }
}

