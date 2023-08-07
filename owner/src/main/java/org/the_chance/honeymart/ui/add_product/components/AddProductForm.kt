package org.the_chance.honeymart.ui.add_product.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.add_product.AddProductUiState
import org.the_chance.honeymart.ui.add_product.showButton
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductForm(
    state: AddProductUiState,
    onProductNameChanged: (String) -> Unit,
    onProductPriceChanged: (String) -> Unit,
    onProductDescriptionChanged: (String) -> Unit,
    onClickAddProduct: (name: String, price: Double, description: String) -> Unit,
    omImageSelected: (List<String>) -> Unit,
    onClickRemoveSelectedImage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(4),
        onResult = { uris ->
            val newImageStrings = uris.map { uri -> uri.toString() }
            val updatedImages = state.productImages + newImageStrings
            omImageSelected(updatedImages)
        }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space24
            ),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_cart),
                contentDescription = "Icon Cart",
                modifier = Modifier
                    .padding(end = MaterialTheme.dimens.space8)
                    .size(MaterialTheme.dimens.icon24),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Add New Product",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        }
        /*FormTextField(
            text = state.productName,
            modifier = Modifier.padding(bottom = MaterialTheme.dimens.space16),
            hint = "Product name",
            keyboardType = KeyboardType.Text,
            onValueChange = onProductNameChanged,
            errorMessage = when (state.productNameState) {
                ValidationState.BLANK_TEXT_FIELD -> "Product name can't be blank"
                ValidationState.SHORT_LENGTH_TEXT -> "Product name is too short"
                else -> ""
            }
        )
        FormTextField(
            text = state.productPrice,
            modifier = Modifier.padding(bottom = MaterialTheme.dimens.space16),
            hint = "Price",
            keyboardType = KeyboardType.Number,
            onValueChange = onProductPriceChanged,
            errorMessage = when (state.productPriceState) {
                ValidationState.BLANK_TEXT_FIELD -> "Product price can't be blank"
                ValidationState.INVALID_PRICE -> "Invalid product price"
                else -> ""
            }
        )
        FormTextField(
            text = state.productDescription,
            modifier = Modifier.padding(bottom = MaterialTheme.dimens.space16),
            hint = "Description",
            keyboardType = KeyboardType.Text,
            onValueChange = onProductDescriptionChanged,
            errorMessage = when (state.productDescriptionState) {
                ValidationState.BLANK_TEXT_FIELD -> "Product description can't be blank"
                ValidationState.SHORT_LENGTH_TEXT -> "Product description is too short"
                else -> ""
            }
        )*/
        Text(
            modifier = Modifier.padding(
                top = MaterialTheme.dimens.space24,
                start = MaterialTheme.dimens.space16
            ),
            text = "Add product image",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Center,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.space16)
        ) {
            LazyVerticalGrid(columns = GridCells.Adaptive(102.dp)) {
                items(state.productImages.size + 1) { index ->
                    if (index < state.productImages.size) {
                        Column(
                            modifier = Modifier.padding(MaterialTheme.dimens.space4)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(102.dp)
                                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(state.productImages[index]),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "Image of product",
                                )
                                IconButton(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(MaterialTheme.dimens.space4),
                                    onClick = {
                                        onClickRemoveSelectedImage(state.productImages[index])
                                    },
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
                    } else if (index <= 3) {
                        Card(
                            modifier = Modifier
                                .size(102.dp)
                                .padding(4.dp),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer),
                            onClick = {
                                multiplePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .size(MaterialTheme.dimens.icon48),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.icon_add_product),
                                    contentDescription = "Icon Add",
                                    modifier = Modifier.size(MaterialTheme.dimens.icon24),
                                    tint = black60
                                )
                            }
                        }
                    }
                }
            }
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
            label = "Add",
            iconPainter = painterResource(R.drawable.icon_add_product),
            onClick = {
                onClickAddProduct(
                    state.productName,
                    state.productPrice.toDouble(),
                    state.productDescription
                )
            }
        )
    }
}