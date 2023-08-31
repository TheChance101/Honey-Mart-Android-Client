package org.the_chance.honeymart.ui.features.category.content

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import org.the_chance.honeymart.ui.features.category.composable.AddImageButton
import org.the_chance.honeymart.ui.features.category.composable.ItemImageProduct
import org.the_chance.honeymart.ui.features.category.showButton
import org.the_chance.honeymart.ui.util.handleImageSelection
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.theme.dimens

const val MAX_IMAGES = 4

@Composable
fun AddProductContent(
    state: CategoriesUiState,
    listener: CategoriesInteractionsListener,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(MAX_IMAGES),
        onResult = { it.handleImageSelection(context, state, listener::onImagesSelected) }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.onTertiary,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        FormHeader(
            title = stringResource(R.string.add_new_product),
            iconPainter = painterResource(id = R.drawable.icon_add_product)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {
            FormTextField(
                text = state.newProducts.name,
                hint = stringResource(R.string.product_name),
                keyboardType = KeyboardType.Text,
                onValueChange = listener::onProductNameChanged,
                errorMessage = when (state.newProducts.productNameState) {
                    ValidationState.BLANK_TEXT_FIELD -> stringResource(R.string.product_name_can_t_be_blank)
                    ValidationState.SHORT_LENGTH_TEXT -> stringResource(R.string.product_name_is_too_short)
                    else -> ""
                }
            )
            Log.e("sara", state.newProducts.toString())
            FormTextField(
                text = state.newProducts.price,
                hint = stringResource(R.string.price),
                keyboardType = KeyboardType.Number,
                onValueChange = listener::onProductPriceChanged,
                errorMessage = when (state.newProducts.productPriceState) {
                    ValidationState.BLANK_TEXT_FIELD -> stringResource(R.string.product_price_can_t_be_blank)
                    ValidationState.INVALID_PRICE -> stringResource(R.string.invalid_product_price)
                    else -> ""
                }
            )
            FormTextField(
                text = state.newProducts.description,
                hint = stringResource(R.string.description),
                keyboardType = KeyboardType.Text,
                onValueChange = listener::onProductDescriptionChanged,
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
            text = stringResource(R.string.add_product_image),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Center,
        )
        Row(
            modifier = Modifier
                .height(256.dp)
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.space16)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(MaterialTheme.dimens.card),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
            ) {
                items(items = state.newProducts.images) { image ->
                    ItemImageProduct(image, listener::onClickRemoveSelectedImage)
                }
                if (state.newProducts.images.size < MAX_IMAGES) {
                    item {
                        AddImageButton(multiplePhotoPickerLauncher)
                    }
                }

                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    HoneyFilledButton(
                        modifier = Modifier.padding(
                            horizontal = MaterialTheme.dimens.space16,
                            vertical = MaterialTheme.dimens.space24
                        ),
                        label = stringResource(R.string.add),
                        onClick = { listener.addProduct(state) },
                        icon = R.drawable.icon_add_to_cart,
                        isLoading = state.isLoading,
                        isButtonEnabled = state.newProducts.showButton()
                    )
                }
            }
        }
    }
}

