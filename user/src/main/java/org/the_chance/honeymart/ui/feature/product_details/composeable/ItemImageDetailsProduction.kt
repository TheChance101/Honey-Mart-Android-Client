package org.the_chance.honeymart.ui.feature.product_details.composeable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.HoneyMartTheme


@Composable
fun ItemImageDetailsProduction(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.size(88.dp)
    ) {
        ImageNetwork(
            imagePainter = rememberAsyncImagePainter(model = imageUrl), modifier = Modifier.fillMaxSize()
        )
    }

}

@Preview
@Composable
private fun ItemImageDetailsProductionPreview() {
    HoneyMartTheme {
        ItemImageDetailsProduction(
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/1/13/Supermarkt.jpg"
        )
    }
}