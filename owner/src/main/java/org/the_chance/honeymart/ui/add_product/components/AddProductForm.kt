package org.the_chance.honeymart.ui.add_product.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.add_product.AddProductInteractionListener
import org.the_chance.honeymart.ui.add_product.AddProductUiState
import org.the_chance.honeymart.ui.add_product.showButton
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

private const val MAX_IMAGES = 4

@Composable
fun AddProductForm(
    state: AddProductUiState,
    listener: AddProductInteractionListener,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(MAX_IMAGES),
        onResult = { handleImageSelection(it, context, state, listener::onImagesSelected) }
    )

    Column(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.space24,
                horizontal = MaterialTheme.dimens.space16,
            )
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        AddProductHeader(
            title = stringResource(R.string.add_new_product),
            iconPainter = painterResource(id = R.drawable.icon_add_product)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {
            FormTextField(
                text = state.name,
                hint = stringResource(R.string.product_name),
                keyboardType = KeyboardType.Text,
                onValueChange = listener::onProductNameChanged,
                errorMessage = when (state.productNameState) {
                    ValidationState.BLANK_TEXT_FIELD -> stringResource(R.string.product_name_can_t_be_blank)
                    ValidationState.SHORT_LENGTH_TEXT -> stringResource(R.string.product_name_is_too_short)
                    else -> ""
                }
            )
            FormTextField(
                text = state.price,
                hint = stringResource(R.string.price),
                keyboardType = KeyboardType.Number,
                onValueChange = listener::onProductPriceChanged,
                errorMessage = when (state.productPriceState) {
                    ValidationState.BLANK_TEXT_FIELD -> stringResource(R.string.product_price_can_t_be_blank)
                    ValidationState.INVALID_PRICE -> stringResource(R.string.invalid_product_price)
                    else -> ""
                }
            )
            FormTextField(
                text = state.description,
                hint = stringResource(R.string.description),
                keyboardType = KeyboardType.Text,
                onValueChange = listener::onProductDescriptionChanged,
                errorMessage = when (state.productDescriptionState) {
                    ValidationState.BLANK_TEXT_FIELD -> stringResource(R.string.product_description_can_t_be_blank)
                    ValidationState.SHORT_LENGTH_TEXT -> stringResource(R.string.product_description_is_too_short)
                    else -> ""
                }
            )
        }
        Text(
            modifier = Modifier.padding(
                top = MaterialTheme.dimens.space24,
                start = MaterialTheme.dimens.space16
            ),
            text = stringResource(R.string.add_product_image),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Center,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.space16)
        ) {
            SelectedImagesGrid(
                images = state.images,
                onClickRemoveSelectedImage = listener::onClickRemoveSelectedImage,
                multiplePhotoPickerLauncher = multiplePhotoPickerLauncher,
                maxImages = MAX_IMAGES
            )
        }
        Spacer(modifier = Modifier.weight(1F))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.space48),
            contentAlignment = Alignment.Center
        ) {
            Loading(
                state = state.isLoading,
                modifier = Modifier.size(MaterialTheme.dimens.smallLottieLoading)
            )
        }
        HoneyFilledIconButton(
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space24
            ),
            isEnable = state.showButton(),
            label = stringResource(R.string.add),
            iconPainter = painterResource(R.drawable.icon_add_to_cart),
            onClick = { listener.addProduct(state) }
        )
    }
}

private fun handleImageSelection(
    uris: List<Uri>,
    context: Context,
    state: AddProductUiState,
    onImageSelected: (List<ByteArray>) -> Unit
) {
    val imageByteArrays = uris.mapNotNull { uri ->
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            inputStream.readBytes()
        }
    }
    val updatedImages = state.images + imageByteArrays
    onImageSelected(updatedImages)
}