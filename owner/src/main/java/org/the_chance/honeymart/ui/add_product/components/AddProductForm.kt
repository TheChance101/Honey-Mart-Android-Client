package org.the_chance.honeymart.ui.add_product.components

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.add_product.AddProductUiState
import org.the_chance.honeymart.ui.add_product.showButton
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.composables.Loading
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )
            .verticalScroll(rememberScrollState()),
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
        FormTextField(
            text = state.productName,
            modifier = Modifier.padding(bottom = MaterialTheme.dimens.space8),
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
            modifier = Modifier.padding(bottom = MaterialTheme.dimens.space8),
            hint = "Price",
            keyboardType = KeyboardType.Number,
            onValueChange = onProductPriceChanged,
            errorMessage = when (state.productPriceState) {
                ValidationState.BLANK_TEXT_FIELD -> "Product price can't be blank"
                else -> ""
            }
        )
        FormTextField(
            text = state.productDescription,
            modifier = Modifier.padding(bottom = MaterialTheme.dimens.space8),
            hint = "Description",
            keyboardType = KeyboardType.Text,
            onValueChange = onProductDescriptionChanged,
            errorMessage = when (state.productDescriptionState) {
                ValidationState.BLANK_TEXT_FIELD -> "Product description can't be blank"
                ValidationState.SHORT_LENGTH_TEXT -> "Product description is too short"
                else -> ""
            }
        )
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
        Card(
            modifier = Modifier
                .padding(
                    top = MaterialTheme.dimens.space16,
                    start = MaterialTheme.dimens.space16
                )
                .size(102.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer),
            onClick = { }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .size(MaterialTheme.dimens.icon48),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_add_product),
                    contentDescription = "Icon Cart",
                    modifier = Modifier.size(MaterialTheme.dimens.icon24),
                    tint = black60
                )
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