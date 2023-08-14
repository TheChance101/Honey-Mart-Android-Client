package org.the_chance.honeymart.ui.features.add_product.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.design_system.R

@Composable
fun ItemImageProduct(
    image: ByteArray,
    onClickRemove: (ByteArray) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(MaterialTheme.dimens.card),
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colorScheme.tertiaryContainer),
            painter = rememberAsyncImagePainter(image),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.image_of_product),
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(MaterialTheme.dimens.space4),
            onClick = { onClickRemove(image) },
            backgroundColor = black16
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(R.string.icon_delete_image),
                tint = Color.White
            )
        }
    }
}