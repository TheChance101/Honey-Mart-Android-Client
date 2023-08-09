package org.the_chance.honeymart.ui.add_product.components

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
fun ImageGrid(
    images: List<ByteArray>,
    onClickRemoveSelectedImage: (ByteArray) -> Unit,
    multiplePhotoPickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>,
    maxImages: Int
 ) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(MaterialTheme.dimens.card),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
    ) {
        items(items = images) { image ->
            ImageBox(image, onClickRemoveSelectedImage)
        }
        if (images.size < maxImages) {
            item {
                AddImageButton(multiplePhotoPickerLauncher)
            }
        }
    }
}