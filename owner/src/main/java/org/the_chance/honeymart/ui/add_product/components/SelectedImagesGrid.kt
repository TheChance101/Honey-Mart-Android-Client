package org.the_chance.honeymart.ui.add_product.components

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun SelectedImagesGrid(
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
            ItemImageProduct(image, onClickRemoveSelectedImage)
        }
        if (images.size < maxImages) {
            item {
                AddImageButton(multiplePhotoPickerLauncher)
            }
        }
    }
}