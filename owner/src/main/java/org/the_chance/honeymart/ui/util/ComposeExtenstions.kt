package org.the_chance.honeymart.ui.util

import android.content.Context
import android.net.Uri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.the_chance.honeymart.ui.features.category.CategoriesUiState


fun <T> LifecycleOwner.collect(flow: Flow<T>, action: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                action.invoke(it)
            }
        }
    }
}

fun List<Uri>.handleImageSelection(
    context: Context,
    state: CategoriesUiState,
    onImageSelected: (List<ByteArray>) -> Unit
) {
    val imageByteArrays = this.mapNotNull { uri ->
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            inputStream.readBytes()
        }
    }
    val updatedImages = state.newProducts.images + imageByteArrays
    onImageSelected(updatedImages)
}

fun Double.toPriceFormat(): String = "$this$"
fun Int.toCountFormat(): String = "$this items"
fun Int.toCountProductFormat(): String = "$this Product"