package org.the_chance.honeymart.ui.features.product_details.composables


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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.components.FormHeader
import org.the_chance.honeymart.ui.components.FormTextField
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyOutlineButton
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

//private const val MAX_IMAGES = 4

@Composable
fun ProductDetailsContent(
    titleScreen:String,
    confirmButton:String,
    cancelButton:String,
    state: CategoriesUiState,
    listener: CategoriesInteractionsListener,
    modifier: Modifier = Modifier,
) {
//    val context = LocalContext.current
////    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
////        contract = ActivityResultContracts.PickMultipleVisualMedia(MAX_IMAGES),
////        onResult = { handleImageSelection(it, context, state, listener::onImagesSelected) }
////    )

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
        FormHeader(
            title = titleScreen,
            iconPainter = painterResource(id = R.drawable.icon_add_product)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {
            FormTextField(
                text = state.productDetails.productName,
                hint = stringResource(R.string.product_name),
                keyboardType = KeyboardType.Text,
                onValueChange = listener::onUpdateProductName,
                errorMessage = when (state.newProducts.productNameState) {
                    ValidationState.BLANK_TEXT_FIELD -> stringResource(R.string.product_name_can_t_be_blank)
                    ValidationState.SHORT_LENGTH_TEXT -> stringResource(R.string.product_name_is_too_short)
                    else -> ""
                }
            )
            FormTextField(
                text = state.productDetails.productPrice,
                hint = stringResource(R.string.price),
                keyboardType = KeyboardType.Number,
                onValueChange = listener::onUpdateProductPrice,
                errorMessage = when (state.newProducts.productPriceState) {
                    ValidationState.BLANK_TEXT_FIELD -> stringResource(R.string.product_price_can_t_be_blank)
                    ValidationState.INVALID_PRICE -> stringResource(R.string.invalid_product_price)
                    else -> ""
                }
            )
            FormTextField(
                text = state.productDetails.productsQuantity,
                hint = stringResource(R.string.description),
                keyboardType = KeyboardType.Text,
                onValueChange = listener::onUpdateProductDescription,
                errorMessage = when (state.newProducts.productDescriptionState) {
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
            text = stringResource(R.string.product_image),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Center,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.space16)
        ) {
//            SelectedImagesGrid(
//                images = state.images,
//                onClickRemoveSelectedImage = listener::onClickRemoveSelectedImage,
//                multiplePhotoPickerLauncher = multiplePhotoPickerLauncher,
//                maxImages = MAX_IMAGES
//            )
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Spacer(modifier = Modifier.weight(1F))
            HoneyFilledButton(modifier = Modifier.width(146.dp),
                label = confirmButton, onClick = { /*TODO*/ })
            HoneyOutlineButton(onClick = { /*TODO*/ }, label = cancelButton)
        }
    }
}

//private fun handleImageSelection(
//    uris: List<Uri>,
//    context: Context,
//    state: ProductsUiState,
//    onImageSelected: (List<ByteArray>) -> Unit
//) {
//    val imageByteArrays = uris.mapNotNull { uri ->
//        context.contentResolver.openInputStream(uri)?.use { inputStream ->
//            inputStream.readBytes()
//        }
//    }
//    val updatedImages = state.images + imageByteArrays
//    onImageSelected(updatedImages)
//}