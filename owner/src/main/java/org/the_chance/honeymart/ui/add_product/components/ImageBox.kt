package org.the_chance.honeymart.ui.add_product.components

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
import coil.compose.rememberAsyncImagePainter
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ImageBox(
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
            contentDescription = "Image of product",
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
                contentDescription = "Icon Delete Image",
                tint = Color.White
            )
        }
    }
}