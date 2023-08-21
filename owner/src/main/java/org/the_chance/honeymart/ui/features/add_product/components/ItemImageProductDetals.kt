package org.the_chance.honeymart.ui.features.add_product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ItemImageProductDetails(
    image: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(MaterialTheme.dimens.card),
    ) {
        ImageNetwork(
            imageUrl = image,
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colorScheme.tertiaryContainer),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.image_of_product),
        )
    }
}